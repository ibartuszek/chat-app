import { ActionType, IUser } from "./interfaces";

export const fetchUsers = async (dispatch: any) => {
  const data = await loadMockUsers()
  return dispatch({
    type: ActionType.LoadUsers,
    payload: data
  })
};

async function loadMockUsers(): Promise<Array<IUser>> {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve(mockUsers());
    }, 3000);
  });
}

function mockUsers(): Array<IUser> {
  return [
    {
      id: "609e2343d4b6a54baa32c475",
      name: "Spiderman",
      email: "peter.parker@spider-net.com",
      active: true,
    },
    {
      id: "609e2376d4b6a54baa32c476",
      name: "Batman",
      email: "john.wayner@cave.com",
      active: true,
    },
    {
      id: "609e2b83d4b6a54baa32c477",
      name: "Wolverine",
      email: "james.howlet@xmen.com",
      active: false,
    },
    {
      id: "609e2c45d4b6a54baa32c478",
      name: "Black Panther",
      email: "k_shamba@wakanda.com",
      active: false,
    },
    {
      id: "609e2cf9d4b6a54baa32c479",
      name: "Hulk",
      email: "bruce.banner@ultimate.com",
      active: false,
    },
  ];
}
