"use client"

function FolderItem({ folder, isSelected, onSelect, onDelete }) {
  const handleDelete = (e) => {
    e.stopPropagation()
    onDelete(folder.folder_id)
  }

  return (
    <div
      className={`list-group-item list-group-item-action d-flex justify-content-between align-items-center ${isSelected ? "active" : ""}`}
      onClick={() => onSelect(folder)}
    >
      <div>
        <span>{folder.name}</span>
        <span className="badge bg-secondary ms-2">{folder.itemCount || 0}</span>
      </div>
      <div className="btn-group">
        <button className="btn btn-sm btn-outline-danger" onClick={handleDelete}>
          <i className="bi bi-trash me-1"></i>
          Eliminar
        </button>
      </div>
    </div>
  )
}

export default FolderItem
