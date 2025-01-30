import TodoApiClient, { TodoItem } from "../TodoApiClient.ts";
import React, { ReactNode } from "react";

interface TodoListProps {
  todoItems: TodoItem[];
  apiClient: TodoApiClient;
  setTodoItems: React.Dispatch<React.SetStateAction<TodoItem[]>>;
}

function TodoList({
  todoItems,
  apiClient,
  setTodoItems,
}: TodoListProps): ReactNode {
  const updateTodoList = () => {
    apiClient.getAllTodoItems().then((items) => setTodoItems(items));
  };

  return (
    <div data-testid="TodoListRoot">
      {todoItems.map((item) => {
        return (
          <div key={item.id}>
            <div>
              title: {item.title}
            </div>
            <input
              role={item.id}
              name={item.id}
              type={"checkbox"}
              defaultChecked={item.done}
              onChange={() => {
                const updateData = {
                  id: item.id,
                  title: item.title,
                  done: !item.done,
                };
                apiClient.updateItem(updateData).then(() => {
                  updateTodoList();
                });
              }}
            />
          </div>
        );
      })}
    </div>
  );
}
export default TodoList;
