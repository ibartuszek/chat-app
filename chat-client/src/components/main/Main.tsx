import React from "react";
import Users from "./users/Users";

export default function Main() {
  return (
    <React.Fragment>
      <div className="sideCar col-4 p-3">
        <Users />
      </div>
    </React.Fragment>
  );
}
