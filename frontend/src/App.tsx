import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import TodoApiClient, {TodoItem} from "./TodoApiClient.ts";

function App() {
  const [count, setCount] = useState(0)

  const apiClient: TodoApiClient = new TodoApiClient("http://localhost:8080")
  const newTodo: TodoItem = { id: "15AE4C25-2E9E-4FAA-81A4-BC913A0F3BDF", title: "シマエナガに餌やり", done: false }
  const updatedTodo: TodoItem = { ...newTodo, done: true }

  return (
    <>
      <button
        onClick={async () => {
          console.log(await apiClient.getAllTodoItems())
        }}
      >
        getAllTodoItems
      </button>
      <button
        onClick={async () => {
          console.log(await apiClient.newTodoItem(newTodo))
        }}
      >
        newTodoItem
      </button>
      <button
        onClick={async () => {
          console.log(await apiClient.getTodoItemById(newTodo.id))
        }}
      >
        getTodoItemById
      </button>
      <button
        onClick={async () => {
          console.log(await apiClient.updateItem(updatedTodo))
        }}
      >
        updateItem
      </button>
      <button
        onClick={async () => {
          console.log(await apiClient.getAllTodoItems())
        }}
      >
        getAllTodoItems
      </button>
      <button
        onClick={async () => {
          console.log(await apiClient.deleteItem(newTodo.id))
        }}
      >
        deleteItem
      </button>
      <button
        onClick={async () => {
          console.log(await apiClient.getAllTodoItems())
        }}
      >
        getAllTodoItems
      </button>
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo"/>
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo"/>
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.tsx</code> and save to test HMR
        </p>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  )
}

export default App
