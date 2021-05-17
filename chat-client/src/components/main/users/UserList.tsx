import React from "react";
import { IUser } from "../../../store/interfaces";

export default function UserList(props: { users: Array<IUser> }) {
  return (
    <React.Fragment>
      {props.users.map((user: IUser, index) => {
        return <div key={index}>{user.name}</div>;
      })}
    </React.Fragment>
  );
}
