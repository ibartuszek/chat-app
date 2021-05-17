import React from "react";
import { IUser } from "../../../store/interfaces";
import { Store } from "../../../store/Store";
import { fetchUsers } from "../../../store/UserActions";
import Spinner from "../Spinner";
import UserList from "./UserList";

export default function Users() {
  const { state, dispatch } = React.useContext(Store);
  const users = state.users as Array<IUser>;
  const usersAreLoaded = state.usersAreLoaded as Boolean;

  React.useEffect(() => {
    if (!usersAreLoaded) {
      fetchUsers(dispatch);
    }
  });

  return (
    <div className="users">
      <h2>Users</h2>
      {!usersAreLoaded ? <Spinner /> : <UserList users={users} />}
    </div>
  );
}
