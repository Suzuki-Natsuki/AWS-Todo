import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import App from "./App.tsx";
import TodoApiClient from "./TodoApiClient.ts";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <App apiClient={new TodoApiClient("")} />
  </StrictMode>,
);
