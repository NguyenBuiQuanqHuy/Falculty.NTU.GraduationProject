document.addEventListener("DOMContentLoaded", function () {
    const rowsPerPage = 5;
    const table = document.querySelector("table tbody");
    const rows = table.querySelectorAll("tr");
    const pagination = document.querySelector(".pagination");

    let currentPage = 1;
    const totalPages = Math.ceil(rows.length / rowsPerPage);
    const maxVisiblePages = 5; // Số nút trang hiển thị

    function showPage(page) {
        currentPage = Math.max(1, Math.min(page, totalPages));
        const start = (currentPage - 1) * rowsPerPage;
        const end = start + rowsPerPage;

        rows.forEach((row, index) => {
            row.style.display = (index >= start && index < end) ? "" : "none";
        });

        updatePagination();
    }

    function updatePagination() {
        pagination.innerHTML = "";

        // Nút Trước
        const prevBtn = document.createElement("button");
        prevBtn.textContent = "Trước";
        prevBtn.disabled = currentPage === 1;
        prevBtn.style.margin = "0 5px";
        prevBtn.addEventListener("click", () => showPage(currentPage - 1));
        pagination.appendChild(prevBtn);

        // Tính toán phạm vi nút trang hiển thị
        let startPage = Math.max(1, currentPage - Math.floor(maxVisiblePages / 2));
        let endPage = startPage + maxVisiblePages - 1;
        if (endPage > totalPages) {
            endPage = totalPages;
            startPage = Math.max(1, endPage - maxVisiblePages + 1);
        }

        // Nút số trang
        for (let i = startPage; i <= endPage; i++) {
            const btn = document.createElement("button");
            btn.textContent = i;
            btn.className = (i === currentPage) ? "active" : "";
            btn.style.margin = "0 3px";
            btn.style.padding = "5px 10px";
            btn.style.cursor = "pointer";
            btn.addEventListener("click", () => showPage(i));
            pagination.appendChild(btn);
        }

        // Nút Sau
        const nextBtn = document.createElement("button");
        nextBtn.textContent = "Sau";
        nextBtn.disabled = currentPage === totalPages;
        nextBtn.style.margin = "0 5px";
        nextBtn.addEventListener("click", () => showPage(currentPage + 1));
        pagination.appendChild(nextBtn);
    }

    showPage(currentPage);
});