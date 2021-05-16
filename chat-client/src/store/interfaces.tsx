export type Dispatch = React.Dispatch<IAction>;

export enum ActionType {
  LoadUsers = "LOAD_USERS",
}

export interface IAction {
  type: ActionType;
  payload: Array<IUser>;
}

export interface IState {
  users: Array<IUser>;
  usersAreLoaded: Boolean;
}

export interface IUser {
  id: String;
  name: String;
  email: String;
  active: Boolean;
}
