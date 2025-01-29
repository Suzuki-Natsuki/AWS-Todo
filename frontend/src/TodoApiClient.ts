interface TodoItem {
    id: string;
    title: string;
    done: boolean;
}

class TodoApiClient {
    apiUrl: string;

    constructor(apiRoot: string) {
        this.apiUrl = apiRoot
    }

    getAllTodoItems(): Promise<TodoItem[]> {
        return fetch(`${this.apiUrl}/api/todo`)
            .then(res => res.json())
            .then(json => json.data)

        // return Promise.resolve
    }

    getTodoItemById(id: string): Promise<TodoItem> {
        return fetch(`${this.apiUrl}/api/todo/${id}`)
            .then(res => res.json())
            .then(json => json.data)
            .then(data => data.find((todo: TodoItem) => todo.id === id))
    }

    newTodoItem(newTodo: TodoItem): Promise<TodoItem[]> {
        return fetch(`${this.apiUrl}/api/todo`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newTodo),
        })
            .then(res => res.json())
            .then(json => json.data);
    }

    // newTodoItem(newTodo: TodoItem): Promise<TodoItem[]> {
    //     return fetch(`${this.apiUrl}/api/todo`, {
    //         method: 'POST',
    //         headers: {
    //             'Content-Type': 'application/json',
    //         },
    //         body: JSON.stringify(newTodo),
    //     })
    //         .then(res => res.json())
    //         .then(json => json.data)
    // }

    deleteItem(id: string) {
        throw new Error("TODO")
    }

    updateItem(updatedItem: TodoItem) {
        throw new Error("TODO")
    }
}

export default TodoApiClient
