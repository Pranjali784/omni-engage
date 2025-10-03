// App.js (Corrected)

import React, { useState, useEffect } from "react";
import axios from "axios";
import "./App.css";

function App() {
  const [activeTab, setActiveTab] = useState("conversations");
  const [conversations, setConversations] = useState([]);
  const [selectedConversation, setSelectedConversation] = useState(null);
  const [reply, setReply] = useState("");
  const [filters, setFilters] = useState({ channel: "", agentName: "", status: "" });
  const [agents, setAgents] = useState([]);
  const [loadingAgents, setLoadingAgents] = useState(false);

  // Simplified: Always use the /filter endpoint.
  // The backend will handle empty filters by returning all conversations.
  const fetchConversations = async () => {
    try {
      const params = new URLSearchParams();
      if (filters.channel) params.append("channel", filters.channel);
      if (filters.agentName) params.append("agentName", filters.agentName);
      if (filters.status) params.append("status", filters.status);

      const url = `http://localhost:8081/api/conversations/filter?${params.toString()}`;

      const response = await axios.get(url, {
        auth: { username: "user", password: "password" },
      });

      // Sort conversations by creation date on the frontend for consistency
      const sortedConversations = response.data.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
      setConversations(sortedConversations);

    } catch (err) {
      console.error("Failed to fetch conversations", err);
    }
  };

  // Fetch agents
  const fetchAgents = async () => {
    setLoadingAgents(true);
    try {
      const response = await axios.get("http://localhost:8081/api/agents", {
        auth: { username: "user", password: "password" },
      });
      setAgents(response.data);
    } catch (err) { // **FIX**: Added missing curly braces here
      console.error("Failed to fetch agents", err);
    } finally {
      setLoadingAgents(false);
    }
  };

  // Auto-refresh every 10s
  useEffect(() => {
    if (activeTab === "conversations") {
      fetchConversations();
      const interval = setInterval(fetchConversations, 10000);
      return () => clearInterval(interval);
    } else if (activeTab === "agents") {
      fetchAgents();
      const interval = setInterval(fetchAgents, 10000);
      return () => clearInterval(interval);
    }
  }, [activeTab, filters]);

  // Handle reply send
  const sendReply = async () => {
    if (!selectedConversation || !reply.trim()) return;
    try {
      await axios.post(
        `http://localhost:8081/api/conversations/${selectedConversation.id}/messages`,
        { content: reply, sender: "Agent" },
        { auth: { username: "user", password: "password" } }
      );
      setReply("");
      // Also update selected conversation to show message immediately
      const newMessage = { sender: "Agent", content: reply, timestamp: new Date().toISOString() };
      setSelectedConversation(prev => ({...prev, messages: [...prev.messages, newMessage]}));
      // Re-fetch in the background to keep data in sync
      fetchConversations();
    } catch (err) {
      console.error("Failed to send reply", err);
    }
  };

  // Close conversation
  const closeConversation = async () => {
    if (!selectedConversation) return;
    try {
      await axios.post(
        `http://localhost:8081/api/conversations/${selectedConversation.id}/close`,
        {},
        { auth: { username: "user", password: "password" } }
      );
      setSelectedConversation({ ...selectedConversation, status: "CLOSED" });
      fetchConversations();
    } catch (err) {
      console.error("Failed to close conversation", err);
    }
  };

  // Reopen conversation
  const reopenConversation = async () => {
    if (!selectedConversation) return;
    try {
      await axios.post(
        `http://localhost:8081/api/conversations/${selectedConversation.id}/reopen`,
        {},
        { auth: { username: "user", password: "password" } }
      );
      setSelectedConversation({ ...selectedConversation, status: "OPEN" });
      fetchConversations();
    } catch (err) {
      console.error("Failed to reopen conversation", err);
    }
  };

  return (
    <div className="app-container">
      <h1>Omni-Engage Dashboard</h1>

      {/* Tabs */}
      <div className="tabs">
        <button
          className={activeTab === "conversations" ? "active" : ""}
          onClick={() => setActiveTab("conversations")}
        >
          Conversations
        </button>
        <button
          className={activeTab === "agents" ? "active" : ""}
          onClick={() => setActiveTab("agents")}
        >
          Agents
        </button>
      </div>

      {/* Conversations Dashboard */}
      {activeTab === "conversations" && (
        <div className="dashboard">
          <div className="sidebar">
            <div className="filters">
              <select
                value={filters.channel}
                onChange={(e) => setFilters({ ...filters, channel: e.target.value })}
              >
                <option value="">All Channels</option>
                <option value="EMAIL">Email</option>
                <option value="CHAT">Chat</option>
                <option value="PHONE_CALL">Phone Call</option>
                <option value="INSTAGRAM">Instagram</option>
                <option value="TWITTER">Twitter</option>
                <option value="FACEBOOK">Facebook</option>
                <option value="WHATSAPP">WhatsApp</option>
              </select>

              <input
                type="text"
                placeholder="Agent Name"
                value={filters.agentName}
                onChange={(e) => setFilters({ ...filters, agentName: e.target.value })}
              />

              <select
                value={filters.status}
                onChange={(e) => setFilters({ ...filters, status: e.target.value })}
              >
                <option value="">All Status</option>
                <option value="OPEN">Open</option>
                <option value="CLOSED">Closed</option>
              </select>
            </div>

            <h2>Conversations</h2>
            <ul>
              {conversations.map((c) => (
                <li
                  key={c.id}
                  onClick={() => setSelectedConversation(c)}
                  className={`${c.status === "CLOSED" ? "closed" : ""} ${
                    selectedConversation?.id === c.id ? "active" : ""
                  }`}
                >
                  <strong>{c.customerName}</strong> <br />
                  {c.channel} - {c.status}
                  <br />
                  Agent: {c.agent?.name}
                </li>
              ))}
            </ul>
          </div>

          <div className="chat-window">
            {selectedConversation ? (
              <>
                <h3>
                  Chat with {selectedConversation.customerName} ({selectedConversation.channel})
                </h3>
                <p>
                  <strong>Agent:</strong> {selectedConversation.agent?.name}
                </p>

                <div className="messages">
                  {selectedConversation.messages.map((m, idx) => (
                    <div key={idx} className="message">
                      <strong>{m.sender}:</strong> {m.content} <br />
                      <small>{new Date(m.timestamp).toLocaleString()}</small>
                    </div>
                  ))}
                </div>

                <div className="reply-box">
                  {selectedConversation.status === "CLOSED" ? (
                    <>
                      <button onClick={reopenConversation} className="reopen-btn">
                        Reopen Conversation
                      </button>
                    </>
                  ) : (
                    <>
                      <input
                        type="text"
                        value={reply}
                        onChange={(e) => setReply(e.target.value)}
                        placeholder="Type your reply..."
                      />
                      <button onClick={sendReply}>Send</button>
                      <button onClick={closeConversation} className="close-btn">
                        Close Conversation
                      </button>
                    </>
                  )}
                </div>
              </>
            ) : (
              <p>Select a conversation to view details</p>
            )}
          </div>
        </div>
      )}

      {/* Agents Dashboard */}
      {activeTab === "agents" && (
        <div className="agents-dashboard">
          <h3>Live Agents</h3>
          {loadingAgents ? (
            <p>Loading agents...</p>
          ) : (
            <table>
              <thead>
                <tr>
                  <th>Agent ID</th>
                  <th>Name</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                {agents.map((agent) => (
                  <tr key={agent.id}>
                    <td>{agent.id}</td>
                    <td>{agent.name}</td>
                    <td>
                      <span
                        className={`badge ${
                          agent.status === "AVAILABLE" ? "available" : "busy"
                        }`}
                      >
                        {agent.status}
                      </span>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      )}
    </div>
  );
}

export default App;