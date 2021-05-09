import React from "react";
import { ActionType, IAction, IState } from "./interfaces";

const initialState: IState = {
  people: [],
};

export const Store = React.createContext<IState | any>(initialState);

function reducer(state: IState, action: IAction): IState {
  switch (action.type) {
    case ActionType.LoadPeople:
      return { ...state, people: action.payload };
    default:
      return state;
  }
}

export default function StoreProvider({
  children,
}: JSX.ElementChildrenAttribute): JSX.Element {
  const [state, dispatch] = React.useReducer(reducer, initialState);
  return (
    <Store.Provider value={{ state, dispatch }}>{children}</Store.Provider>
  );
}
