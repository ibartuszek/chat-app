import React from "react";
import ReactDOM from "react-dom";
import Main from "./components/Main";
import "./index.css";
import reportWebVitals from "./reportWebVitals";
import StoreProvider from "./store/Store";

ReactDOM.render(
  <React.StrictMode>
    <StoreProvider>
      <h1 className="p-3">Chat application</h1>
      <div className="container"></div>
      <Main />
    </StoreProvider>
  </React.StrictMode>,
  document.getElementById("root")
);

reportWebVitals();
