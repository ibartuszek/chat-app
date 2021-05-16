import React from 'react'
import { Store } from '../store/Store'
import { fetchUsers } from '../store/UserActions'

export default function Main() {

    const {state, dispatch} = React.useContext(Store)

    React.useEffect(() => {
        if (!state.usersAreLoaded) {
            fetchUsers(dispatch)
        }
    })

    return (
        <div>
           {state.usersAreLoaded ? "Users are loaded" : "Loading users..."} 
        </div>
    )
}
