import TodoApiClient from "./TodoApiClient.ts";

describe("TodoApiClient", () => {
    beforeEach(() => {
        // @ts-ignore
        fetch.resetMocks();
    })

    const todoClient = new TodoApiClient("http://todo-api.example.com")

    it("getAllTodoItemsで空データを取得する", async () => {
        // @ts-ignore
        fetch.mockResponseOnce(JSON.stringify({ data: [] }));

        const actualResponse = await todoClient.getAllTodoItems()

        expect(actualResponse).toEqual([]) // 実際のレスポンスが空の配列であることを確認します。
        expect(fetch).toHaveBeenCalledTimes(1) // fetch関数が1回だけ呼び出されたことを確認します。
        expect(fetch).toHaveBeenCalledWith("http://todo-api.example.com/api/todo") // fetchが正しいURLで呼び出されたことを確認します。
    });

    it("getAllTodoItemsでモックデータを取得する", async () => {
        const initialData = [
            { id: "15AE4C25-2E9E-4FAA-81A4-BC913A0F3BDF", title: "シマエナガに餌やり", done: false },
            { id: "614277CB-CCDF-4D85-AA1B-BE48E8583147", title: "虎に肉を与える", done: true },
        ];
        // @ts-ignore
        fetch.mockResponseOnce(JSON.stringify({ data: initialData }));

        const actualResponse = await todoClient.getAllTodoItems()

        expect(actualResponse).toEqual(initialData) // 配列の全てが返ってくる
        expect(fetch).toHaveBeenCalledTimes(1) // fetch関数が1回だけ呼び出されたことを確認します。
        expect(fetch).toHaveBeenCalledWith("http://todo-api.example.com/api/todo") // fetchが正しいURLで呼び出されたことを確認します。
    });

    it("getTodoItemById", async () => {
        const initialData = [
            { id: "15AE4C25-2E9E-4FAA-81A4-BC913A0F3BDF", title: "シマエナガに餌やり", done: false },
            { id: "614277CB-CCDF-4D85-AA1B-BE48E8583147", title: "虎に肉を与える", done: true },
        ];
        // @ts-ignore
        fetch.mockResponseOnce(JSON.stringify({ data: initialData }));

        const id: string = "15AE4C25-2E9E-4FAA-81A4-BC913A0F3BDF"
        const actualResponse = await todoClient.getTodoItemById(id)

        expect(actualResponse).toEqual({ id: "15AE4C25-2E9E-4FAA-81A4-BC913A0F3BDF", title: "シマエナガに餌やり", done: false }) // 指定したIDのアイテムが変える
        expect(fetch).toHaveBeenCalledTimes(1) // fetch関数が1回だけ呼び出されたことを確認します。
        expect(fetch).toHaveBeenCalledWith("http://todo-api.example.com/api/todo/15AE4C25-2E9E-4FAA-81A4-BC913A0F3BDF") // fetchが正しいURLで呼び出されたことを確認します。

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
