export interface TodoItem {
    id: string;
    title: string;
    done: boolean;
}

class TodoApiClient {
    apiUrl: string;

    constructor(apiRoot: string) {
        this.apiUrl = apiRoot
    }

    async getAllTodoItems(): Promise<TodoItem[]> {
        return fetch(`${this.apiUrl}/api/todo`)
          .then(res => res.json())
    }

    async getTodoItemById(id: string): Promise<TodoItem> {
        return fetch(`${this.apiUrl}/api/todo/${id}`)
            .then(res => res.json())
            // .then((data: TodoItem) => data.find((todo: TodoItem) => todo.id === id))
    }

    async deleteItem(id: string): Promise<void> {
        return fetch(`${this.apiUrl}/api/todo/${id}`, {
            method: 'DELETE',
        }).then(() => Promise.resolve())
    }

    async newTodoItem(newTodo: TodoItem): Promise<void> {
        return fetch(`${this.apiUrl}/api/todo`, {
            method: 'POST',
            body: JSON.stringify(newTodo),
            headers: {"Content-Type": "application/json",}
        }).then(() => Promise.resolve())
    }


    async updateItem(updatedItem: TodoItem): Promise<void> {
        return fetch(`${this.apiUrl}/api/todo`, {
            method: 'PATCH',
            body: JSON.stringify(updatedItem),
            headers: {"Content-Type": "application/json",}
        }).then(() => Promise.resolve())
    }
}

export default TodoApiClient
