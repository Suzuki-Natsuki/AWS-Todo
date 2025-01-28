import {render, screen} from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import App from "./App.tsx";
import TodoApiClient, {TodoItem} from "./TodoApiClient.ts";

const mockApiClient = {
    getAllTodoItems: vi.fn(),
    getTodoItemById: vi.fn(),
    deleteItem: vi.fn(),
    newTodoItem: vi.fn(),
    updateItem: vi.fn(),
} as unknown as TodoApiClient

describe("<App />", () => {
    beforeEach(() => {
        vi.resetAllMocks()
    })

    it("TodoListできる", async () => {
        //arrange
        const todoItems = [
            { id: "15AE4C25-2E9E-4FAA-81A4-BC913A0F3BDF", title: "シマエナガに餌やり", done: false },
            { id: "614277CB-CCDF-4D85-AA1B-BE48E8583147", title: "虎に肉を与える", done: true },
        ]
        // @ts-ignore
        mockApiClient.getAllTodoItems.mockResolvedValue(todoItems)

        //act
        render(<App apiClient={mockApiClient}/>);

        //assert
        expect(await screen.findByText(todoItems[0].title)).toBeInTheDocument()
        expect(await screen.findByText(todoItems[1].title)).toBeInTheDocument()
    });

    it("TodoItem POST できる", async () => {
        const expectedNewTodoItem: Partial<TodoItem> = {
            title: "頑張ってぞい",
            done: false
        }
        mockApiClient.getAllTodoItems
        // @ts-ignore
          .mockResolvedValueOnce([])
        // @ts-ignore
          .mockResolvedValue([{...expectedNewTodoItem, id: "some-uuid"}])
        // @ts-ignore
        mockApiClient.newTodoItem.mockResolvedValue(Promise.resolve())

        render(<App apiClient={mockApiClient}/>);

        await userEvent.type(screen.getByRole("textbox", {name: "New Todo Title"}), "頑張ってぞい")
        await userEvent.click(screen.getByRole("button", {name: "SAVE"}))

        expect(mockApiClient.newTodoItem).toHaveBeenCalledTimes(1)
        expect(mockApiClient.newTodoItem).toHaveBeenCalledWith(expect.objectContaining(expectedNewTodoItem))
        expect(mockApiClient.getAllTodoItems).toHaveBeenCalledTimes(2)
        expect(screen.getByText("頑張ってぞい")).toBeInTheDocument()

    })

    it("TodoItem DELETE できる", async () => {

        mockApiClient.getAllTodoItems
          // @ts-ignore
          .mockResolvedValueOnce([
            { id: "15AE4C25-2E9E-4FAA-81A4-BC913A0F3BDF", title: "シマエナガに餌やり", done: false },
            { id: "614277CB-CCDF-4D85-AA1B-BE48E8583147", title: "虎に肉を与える", done: true }
          ])
          // @ts-ignore
          .mockResolvedValue([
              { id: "614277CB-CCDF-4D85-AA1B-BE48E8583147", title: "虎に肉を与える", done: true }
          ])

        // @ts-ignore
        mockApiClient.deleteItem.mockResolvedValue(Promise.resolve())

        render(<App apiClient={mockApiClient}/>)

        await userEvent.type(screen.getByRole("textbox", {name: "ID to delete"}),"15AE4C25-2E9E-4FAA-81A4-BC913A0F3BDF")
        await userEvent.click(screen.getByRole("button", {name: "DELETE"}))

        expect(mockApiClient.deleteItem).toHaveBeenCalledTimes(1)
        expect(mockApiClient.deleteItem).toHaveBeenCalledWith("15AE4C25-2E9E-4FAA-81A4-BC913A0F3BDF")
        expect(mockApiClient.getAllTodoItems).toHaveBeenCalledTimes(2)
        expect(screen.queryByText("シマエナガに餌やり")).not.toBeInTheDocument()
    })
});
