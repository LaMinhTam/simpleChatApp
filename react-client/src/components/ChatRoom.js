import React, { useEffect, useState } from "react";
import { over } from "stompjs";
import SockJS from "sockjs-client";

var stompClient = null;
const MyChatRoom = () => {
  const [listChat, setListChat] = useState(new Map());
  const [currentSelectChat, setCurrentSelectChat] = useState([]);
  const [tab, setTab] = useState("CHATROOM");
  const [userData, setUserData] = useState({
    userId: "",
    username: "",
    inboxIds: new Set(),
    connected: false,
  });

  useEffect(() => {
    if (userData.connected) {
      connect();
    }
  }, [userData.connected]);

  const handleUsername = (event) => {
    const { value } = event.target;

    setUserData({ ...userData, userId: value });
  };

  const registerUser = () => {
    fetch(`http://localhost:8080/${userData.userId}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((respon) => respon.json())
      .then((data) => {
        setUserData({
          ...userData,
          username: data.username,
          inboxIds: data.inboxIds,
          connected: true,
        });
      });

    fetch(`http://localhost:8080/chatContext/${userData.userId}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((respon) => respon.json())
      .then((data) => {
        const formattedData = new Map();
        data.forEach((inbox) => {
          const messagesMap = new Map(
            inbox.messages.map((message) => [message.id, message])
          );

          formattedData.set(inbox.id, {
            id: inbox.id,
            type: inbox.type,
            participants: inbox.participants,
            messages: messagesMap,
          });
        });

        setListChat(formattedData);
      });

    connect();
  };

  const connect = () => {
    let Sock = new SockJS("http://localhost:8080/ws");
    stompClient = over(Sock);
    stompClient.connect({}, onConnected, onError);
  };

  const onConnected = () => {
    stompClient.subscribe(
      "/user/" + userData.userId + "/message",
      onReceiveMessage
    );

    stompClient.subscribe(
      "/user/" + userData.userId + "/first_message",
      onReceiveFirstInbox
    );
  };

  const onReceiveFirstInbox = (payload) => {
    var payloadData = JSON.parse(payload.body);

    const newChat = {
      id: payloadData.id,
      type: payloadData.type,
      participants: payloadData.participants,
      messages: payloadData.messages,
    };

    setListChat((prevListChat) => {
      const newListChat = new Map(prevListChat);
      newListChat.set(payloadData.id, newChat);
      return new Map(newListChat);
    });

    setUserData((prevUserData) => ({
      ...prevUserData,
      inboxIds: new Set([...prevUserData.inboxIds, payloadData.id]),
    }));
  };

  const onReceiveMessage = (payload) => {
    var payloadData = JSON.parse(payload.body);

    // Create a new message object using the payload data
    const newMessage = {
      id: payloadData.id,
      senderUserId: payloadData.senderUserId,
      content: payloadData.content,
      timestamp: payloadData.timestamp,
    };

    const key = payloadData.receiverInboxId;

    setListChat((prevListChat) => {
      const newListChat = new Map(prevListChat);

      newListChat.get(key).messages.set(payloadData.id, newMessage);

      return new Map(newListChat);
    });
  };

  const onError = (err) => {
    console.log(err);
  };

  const handleMessage = (event) => {
    const { value } = event.target;
    setUserData({ ...userData, message: value });
  };

  const sendMessage = () => {
    var chatMessage = {
      senderUserId: userData.userId,
      senderUserName: userData.username,
      receiverInboxId: currentSelectChat.id,
      content: userData.message,
      timestamp: new Date().toISOString(),
      participants: currentSelectChat.participants,
    };

    stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
  };

  const handleTestSendMessage = () => {
    var chatMessage = {
      senderUserId: userData.userId,
      senderUserName: userData.username,
      receiverInboxId: null,
      content: "Hi",
      timestamp: new Date().toISOString(),
      participants: [
        {
          id: "1",
          username: "Alice",
        },
        {
          id: "3",
          username: "Charlie",
        },
      ],
    };

    stompClient.send("/app/first_message", {}, JSON.stringify(chatMessage));
  };

  return (
    <div className="container">
      {userData.connected ? (
        <div className="chat-box">
          <div className="member-list">
            <ul>
              {Array.from(userData.inboxIds).map((id, index) => (
                <li
                  onClick={() => {
                    setTab(id);
                    setCurrentSelectChat(listChat.get(id));
                  }}
                  className={`member ${tab === id && "active"}`}
                  key={index}
                >
                  {id}
                </li>
              ))}
            </ul>
          </div>
          <div className="chat-content">
            <ul className="chat-messages">
              {Array.from(
                Array.from(currentSelectChat?.messages?.values() || [])
              )?.map((message) => (
                <li key={message.id}>
                  <div
                    className={`message ${
                      userData.userId === message.senderUserId && "self"
                    }`}
                  >
                    <span>
                      {
                        currentSelectChat.participants.find(
                          (participant) =>
                            participant.id === message.senderUserId
                        ).username
                      }
                    </span>
                    :&nbsp;
                    <span>{message.content}</span>
                    &nbsp;
                    <div>
                      <div>{message.timestamp}</div>
                    </div>
                  </div>
                </li>
              ))}
            </ul>

            <div className="send-message">
              <input
                type="text"
                className="input-message"
                placeholder="enter the message"
                value={userData.message}
                onChange={handleMessage}
              />
              <button
                type="button"
                className="send-button"
                onClick={sendMessage}
              >
                send
              </button>
            </div>
          </div>
        </div>
      ) : (
        <div className="register">
          <input
            id="user-name"
            placeholder="Enter your name"
            name="userName"
            value={userData.userId}
            onChange={handleUsername}
            margin="normal"
          />
          <button type="button" onClick={registerUser}>
            connect
          </button>
        </div>
      )}
      <button onClick={() => console.log(listChat)}>list chat</button>
      &nbsp;
      <button onClick={() => console.log(currentSelectChat)}>
        currentSelectChat
      </button>
      &nbsp;
      <button onClick={handleTestSendMessage}>send message Hi to user 1</button>
    </div>
  );
};

export default MyChatRoom;
