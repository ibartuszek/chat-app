import { Component } from 'react';
import moment from 'moment';
import './ChatHistory.css';

class ChatHistory extends Component {
    render() {
        let messages = this.props.messages;
        let messageItems = <div>No previous message</div>
        if (messages!== undefined && messages !== null && messages.length > 0) {
          
          messageItems = messages.map((message, index) => 
            <div key={index} className="row align-items-center chatMessage">
              <div className="col-3 align-self-stretch chatMessageMeta">
                <div>{message.name}</div>
                <div>{moment(message.date).format("HH:MM:SS")}</div>
              </div>
              <div className="col align-self-stretch chatMessageText">{message.message}</div>
            </div>
          );
        }
        return (
          <div className="container my-3 w-75">
            {messageItems}
          </div>
        );
      }

}


export default ChatHistory;
