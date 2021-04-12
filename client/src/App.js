import { Component } from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

const serverAddress = "http://localhost:8080/ws-chat";
const topicToListen = "/topic/public";
const topicToSend = "/app/send";

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {
      name: "John Doe",
      message: "",
      stompClient: null
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.state.stompClient = this.connect();

  }

  connect() {
    console.log(`Client trying to connect to server=${serverAddress}`);
    let sock = new SockJS(serverAddress);
    let stompClient = Stomp.over(sock);
    stompClient.debug = null;

    sock.onopen = function() {
      console.log("Client connected successfully");
    }

    stompClient.connect({}, function (frame) {
      console.log("Connected: " + frame);
      stompClient.subscribe(topicToListen, function (message) {
        console.log(`Message received=${message}`);
      });
    });

    return stompClient;
  }

  handleChange(event) {
    this.setState({
      message: event.target.value
    })
  }

  handleSubmit(event) {
    event.preventDefault();
    console.log(`Send message: name=${this.state.name} message=${this.state.message}`);
    this.sendMessage();
  }

  sendMessage() {
    let msg = this.createMessage();
    this.state.stompClient.send(topicToSend, {}, msg);
  }

  createMessage() {
    const message = {
      name: this.state.name,
      message: this.state.message
    };
    return JSON.stringify(message);
  }

  render() {
    return (
      <div className="body">
        <h1>Chat application</h1>
        <hr/>
        <form className="w-75 mx-auto py-3 row" onSubmit={this.handleSubmit}>
          <label className="col-form-label">Message</label>
          <input type="text" className="form-control col ml-3"
                  value={this.state.value} onChange={this.handleChange} />
          <input type="submit" value="Send" className="btn btn-light ml-3 col-3" />
        </form>
      </div>
    );
  }

}

export default App;
