import { Component } from 'react';

class ChatHistory extends Component {

    render() {
        let messages = this.props.messages;
        let messageItems = <div>No previous message</div>
        if (messages!== undefined && messages !== null && messages.length > 0) {
            messageItems = messages.map((message, index) => 
                <div key={index} className="row">
                    <div className="col-2">{message.name}</div>
                    <div className="col">{message.message}</div>
                </div>
            );
        }
        return (
          <div className="container my-3 w-75 chatHistory">
            {messageItems}
          </div>
        );
      }

}


export default ChatHistory;
