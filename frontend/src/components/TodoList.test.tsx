import { render, screen } from "@testing-library/react";
import TodoList from "./TodoList.tsx";
import TodoApiClient from "../TodoApiClient.ts";

const mockApiClient = {
  getAllTodoItems: vi.fn(),
  getTodoItemById: vi.fn(),
  deleteItem: vi.fn(),
  newTodoItem: vi.fn(),
  updateItem: vi.fn(),
} as unknown as TodoApiClient;

const mockSetTodoItems = vi.fn();

describe("<TodoList />", () => {
  it("シマエナガが正しく表示される", () => {
    const todoItems = [
      {
        id: "15AE4C25-2E9E-4FAA-81A4-BC913A0F3BDF",
        title: "シマエナガに餌やり",
        done: false,
      },
    ];
    render(
      <TodoList
        todoItems={todoItems}
        apiClient={mockApiClient}
        setTodoItems={mockSetTodoItems}
      />,
    );

    expect(screen.getByText(`title: ${todoItems[0].title}`)).toBeInTheDocument();
  });

  it("複数のTodoItemが正しく表示される", () => {
    const todoItems = [
      {
        id: "15AE4C25-2E9E-4FAA-81A4-BC913A0F3BDF",
        title: "シマエナガに餌やり",
        done: false,
      },
      {
        id: "614277CB-CCDF-4D85-AA1B-BE48E8583147",
        title: "虎に肉を与える",
        done: true,
      },
    ];
    render(
      <TodoList
        todoItems={todoItems}
        apiClient={mockApiClient}
        setTodoItems={mockSetTodoItems}
      />,
    );

    expect(screen.getByText(`title: ${todoItems[0].title}`)).toBeInTheDocument();
    expect(screen.getByText(`title: ${todoItems[1].title}`)).toBeInTheDocument();
  });
});
