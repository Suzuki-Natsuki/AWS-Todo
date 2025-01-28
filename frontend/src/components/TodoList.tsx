import {TodoItem} from "../TodoApiClient.ts";
import {ReactNode} from "react";

interface TodoListProps {
  todoItems: TodoItem[]
}
function TodoList({todoItems}: TodoListProps): ReactNode {
  return (
    <>{
      todoItems.map((item) => {
        return <div key={item.id}>title: {item.title}, id: {item.id}</div>
      })
    }</>)
}
export default TodoList
