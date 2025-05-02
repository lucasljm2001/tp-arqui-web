"use client"
import ItemForm from "./ItemForm"

function ItemRow({ item, isEditing, onToggle, onEdit, onDelete, onUpdate, onCancelEdit }) {
  // Función para formatear la fecha
  const formatDate = (dateString) => {
    if (!dateString) return "Fecha desconocida"

    const date = new Date(dateString)
    return date.toLocaleString("es-ES", {
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
      hour: "2-digit",
      minute: "2-digit",
    })
  }

  return (
    <div className="list-group-item">
      {isEditing ? (
        <ItemForm item={item} onSubmit={(description) => onUpdate(item.item_id, description)} onCancel={onCancelEdit} />
      ) : (
        <div className="d-flex flex-column">
          <div className="d-flex justify-content-between align-items-center">
            <div className="d-flex align-items-center">
              <div className="form-check">
                <input
                  className="form-check-input"
                  type="checkbox"
                  checked={item.state === "DONE"}
                  onChange={() => onToggle(item.item_id)}
                  id={`item-${item.item_id}`}
                />
                <label
                  className={`form-check-label ${item.state === "DONE" ? "text-decoration-line-through text-muted" : ""}`}
                  htmlFor={`item-${item.item_id}`}
                >
                  {item.description}
                </label>
              </div>
            </div>
            <div className="btn-group">
              <button className="btn btn-sm btn-outline-secondary" onClick={() => onEdit(item.item_id)}>
                <i className="bi bi-pencil"></i>
              </button>
              <button className="btn btn-sm btn-outline-danger" onClick={() => onDelete(item.item_id)}>
                <i className="bi bi-trash"></i>
              </button>
            </div>
          </div>
          <div className="mt-1">
            <small className="text-muted">Creado: {formatDate(item.createdAt)}</small>
          </div>
        </div>
      )}
    </div>
  )
}

export default ItemRow
