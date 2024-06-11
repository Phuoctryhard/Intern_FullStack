$(document).ready(function () {
  let students = [];
  // Load dữ liệu từ file JSON
  function loadStudents() {
    $.getJSON("./students.json", function (data) {
      students = data;
      renderTable(students);
    });
  }
  // Render bảng dữ liệu
  function renderTable(data) {
    const tbody = $("#student-table tbody");
    tbody.empty();
    data.forEach((student) => {
      const row = `<tr>
                <td>${student.name}</td>
                <td>${student.age}</td>
            </tr>`;
      tbody.append(row);
    });
  }
  // Sắp xếp bảng theo tên hoặc tuổi
  function sortTableBy(field) {
    if (field == "name") {
      students.sort((a, b) => {
        // Lấy phần tử cuối cùng của tên
        const lastNameA = a[field].split(" ").pop().toLowerCase();
        const lastNameB = b[field].split(" ").pop().toLowerCase();
        if (lastNameA < lastNameB) return -1;
        if (lastNameA > lastNameB) return 1;
        return 0;
      });
    } else {
      students.sort((a, b) => {
        const ageA = parseInt(a[field]);
        const ageB = parseInt(b[field]);
        return ageA - ageB;
      });
    }
    renderTable(students);
  }
  // Tìm kiếm theo tên và bôi đỏ
  $("#search-name-btn").click(function () {
    const searchText = $("#name-search").val().toLowerCase();

    // Kiểm tra nếu input trống
    if (searchText.trim() === "") {
      $("#name-error").text("Vui lòng nhập tên để tìm kiếm.");
      return;
    }
    $("#student-table tbody tr").each(function () {
      const nameCell = $(this).find("td:first").text().toLowerCase();
      if (nameCell.includes(searchText)) {
        $(this).css("background-color", "red");
      } else {
        $(this).css("background-color", "");
      }
    });
  });
  // Lọc theo tuổi và ẩn
  $("#filter-age-btn").click(function () {
    const ageFilter = parseInt($("#age-filter").val(), 10);

    // Kiểm tra nếu giá trị không phải là số dương
    if (isNaN(ageFilter) || ageFilter <= 0) {
      $("#name-errorAge").text("Vui lòng nhập giá trị tuổi dương lớn hơn 0.");
      return;
    }
    $("#student-table tbody tr").each(function () {
      const ageCell = parseInt($(this).find("td:last").text(), 10);
      if (ageCell < ageFilter) {
        $(this).hide();
      } else {
        $(this).show();
      }
    });
  });

  // Nút reset
  $("#reset-btn").click(function () {
    renderTable(students);
    $("#student-table tbody tr").css("background-color", "").show();
    $("#name-search").val("");
    $("#age-filter").val("");
    $("#name-error").text(""); // Reset lại lỗi
    $("#name-errorAge").text("");
  });
  // Xóa lỗi khi thay đổi nội dung trong input name
  $("#name-search").on("input", function () {
    $("#name-error").text("");
  });
  // xóa lỗi khi thay đổi input Age
  $("#age-filter").on("input", function () {
    $("#name-errorAge").text("");
  });
  // Sự kiện click để sắp xếp
  $("#sort-name").click(function () {
    sortTableBy("name");
  });

  $("#sort-age").click(function () {
    sortTableBy("age");
  });

  // Load dữ liệu ban đầu
  loadStudents();
});
