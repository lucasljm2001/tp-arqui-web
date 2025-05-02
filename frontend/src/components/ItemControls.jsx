"use client"

function ItemControls({ filter, onFilterChange, sortBy, onSortChange }) {
  return (
    <div className="d-flex">
      <div className="dropdown me-2">
        <button
          className="btn btn-outline-secondary dropdown-toggle"
          type="button"
          id="filterDropdown"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          {filter ? (filter === "TODO" ? "Pendientes" : "Completados") : "Todos"}
        </button>
        <ul className="dropdown-menu" aria-labelledby="filterDropdown">
          <li>
            <button className="dropdown-item" onClick={() => onFilterChange("")}>
              Todos
            </button>
          </li>
          <li>
            <button className="dropdown-item" onClick={() => onFilterChange("TODO")}>
              Pendientes
            </button>
          </li>
          <li>
            <button className="dropdown-item" onClick={() => onFilterChange("DONE")}>
              Completados
            </button>
          </li>
        </ul>
      </div>

      <div className="dropdown">
        <button
          className="btn btn-outline-secondary dropdown-toggle"
          type="button"
          id="sortDropdown"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          Ordenar
        </button>
        <ul className="dropdown-menu" aria-labelledby="sortDropdown">
          <li>
            <button className="dropdown-item" onClick={() => onSortChange("createdAt")}>
              Por fecha {sortBy === "createdAt" && (onSortChange.direction === "asc" ? "↑" : "↓")}
            </button>
          </li>
          <li>
            <button className="dropdown-item" onClick={() => onSortChange("description")}>
              Por descripción {sortBy === "description" && (onSortChange.direction === "asc" ? "↑" : "↓")}
            </button>
          </li>
        </ul>
      </div>
    </div>
  )
}

export default ItemControls
