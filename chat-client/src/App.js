import { Component } from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import ChatHistory from './chat/ChatHistory';
import ChatInput from './chat/ChatInput';

const serverAddress = "http://localhost:8080/ws-chat";
const topicToListen = "/topic/public";
const topicToSend = "/app/send";

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {
      stompClient: null,
      messages: []
    };

    this.onMessageReceived = this.onMessageReceived.bind(this);
    this.sendMessage = this.sendMessage.bind(this);

  }

  componentDidMount() {
    let client = this.connect(this.onMessageReceived);
    this.setState({
      stompClient: client
    });
  }

  onMessageReceived(message) {
    console.log(`Message received=${message}`);
    let newMessageList = this.state.messages;
    let newMessage = JSON.parse(message.body);
    newMessageList.push(newMessage);
    this.setState({
      messages: newMessageList
    })
  }

  connect(onMessageReceived) {
    console.log(`Client trying to connect to server=${serverAddress}`);
    let sock = new SockJS(serverAddress);
    let stompClient = Stomp.over(sock);
    stompClient.debug = null;

    sock.onopen = function() {
      console.log("Client connected successfully");
    }

    stompClient.connect({}, function (frame) {
      console.log("Connected: " + frame);
      stompClient.subscribe(topicToListen, onMessageReceived)
    });

    return stompClient;
  }

  sendMessage(message) {
    this.state.stompClient.send(topicToSend, {}, message);
  }

  render() {

    return (
      <div className="body">
        <h1>Chat application</h1>
        <hr/>
        <ChatHistory messages={this.state.messages} />
        <hr/>
        <ChatInput sendMessage={this.sendMessage}/>
      </div>
    );
  }

}

export default App;
