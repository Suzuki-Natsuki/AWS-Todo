import TodoApiClient from "./TodoApiClient.ts";

describe("TodoApiClient", () => {
    it("getAllTodoItems", async () => {
        // @ts-ignore
        fetch.mockResponseOnce(JSON.stringify({ data: [] }));
        const todoClient = new TodoApiClient("http://todo-api.example.com")

        const actualResponse = await todoClient.getAllTodoItems()

        expect(actualResponse).toEqual([]) // 実際のレスポンスが空の配列であることを確認します。
        expect(fetch).toHaveBeenCalledTimes(1) // fetch関数が1回だけ呼び出されたことを確認します。
        expect(fetch).toHaveBeenCalledWith("http://todo-api.example.com/api/todo") // fetchが正しいURLで呼び出されたことを確認します。
    });
    it("getTodoItemById", async () => {
        expect(true).toBeFalsy()
    });
    it("newTodoItem", async () => {
        expect(true).toBeFalsy()
    });
    it("deleteItem", async () => {
        expect(true).toBeFalsy()
    });
    it("updateItem", async () => {
        expect(true).toBeFalsy()
    });
});
