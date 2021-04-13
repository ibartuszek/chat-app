import { Component } from 'react';

class ChatInput extends Component {

    constructor(props) {
        super(props);
        this.state = {
          name: "John Doe",
          message: "",
        };
    
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
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
      let message = this.createMessage();
      this.props.sendMessage(message);
    }
    
    createMessage() {
      const message = {
        date: new Date(),
        name: this.state.name,
        message: this.state.message
      };
      return JSON.stringify(message);
    }

    render() {
        return (
            <form className="w-75 mx-auto py-3 row" onSubmit={this.handleSubmit}>
            <label className="col-form-label">Message</label>
            <input type="text" className="form-control col ml-3"
                    value={this.state.value} onChange={this.handleChange} />
            <input type="submit" value="Send" className="btn btn-light ml-3 col-3" />
          </form>
        );
      }

}

export default ChatInput;
