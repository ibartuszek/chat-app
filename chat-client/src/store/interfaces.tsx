export type Dispatch = React.Dispatch<IAction>;

export enum ActionType {
  LoadPeople = "LOAD_PEOPLE",
}

export interface IAction {
  type: ActionType;
  payload: Array<IPeople>;
}

export interface IState {
  people: Array<IPeople>;
}

export interface IPeople {
  id: string;
  name: string;
}
