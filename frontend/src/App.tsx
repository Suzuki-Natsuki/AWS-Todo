import TodoApiClient from "./TodoApiClient.ts";
import TodoList from "./components/TodoList.tsx";
import { useEffect, useRef, useState } from "react";
import { TodoItem } from "./TodoApiClient.ts";

interface AppProps {
  apiClient: TodoApiClient;
}
function App({ apiClient }: AppProps) {
  const [todoItems, setTodoItems] = useState<TodoItem[]>([]);

  useEffect(() => {
    updateTodoList();
  }, []);

  const updateTodoList = () => {
    apiClient.getAllTodoItems().then((todoItems) => setTodoItems(todoItems));
  };

  const inputRef = useRef<HTMLInputElement>(null);
  const inputIdRef = useRef<HTMLInputElement>(null);

  return (
    <>
      <TodoList
        todoItems={todoItems}
        apiClient={apiClient}
        setTodoItems={setTodoItems}
      />
      <div>
        <input role={"newTodoItem"} ref={inputRef} />
        <button
          onClick={() => {
            const createdItem = {
              id: self.crypto.randomUUID(),
              title: inputRef.current!.value,
              done: false,
            };
            apiClient.newTodoItem(createdItem).then(() => {
              updateTodoList();
            });
          }}
        >
          SAVE
        </button>
      </div>
      <div>
        <input role={"idToDelete"} ref={inputIdRef} />
        <button
          onClick={() => {
            apiClient.deleteItem(inputIdRef.current!.value).then(() => {
              updateTodoList();
            });
          }}
        >
          DELETE
        </button>
      </div>
    </>
  );
}

export default App;
