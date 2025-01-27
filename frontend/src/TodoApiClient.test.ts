import TodoApiClient from "./TodoApiClient.ts";

describe("TodoApiClient", () => {
    it("getAllTodoItems", async () => {
        // @ts-ignore
        fetch.mockResponseOnce(JSON.stringify({ data: [] }));
        const todoClient = new TodoApiClient("http://todo-api.example.com")

        const actualResponse = await todoClient.getAllTodoItems()
        expect(actualResponse).toEqual([])
        expect(fetch).toHaveBeenCalledTimes(1)
        expect(fetch).toHaveBeenCalledWith("http://todo-api.example.com/api/todo")
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