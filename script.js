$(document).ready(function () {
  let students = JSON.parse(localStorage.getItem("students")) || [];
  let editIndex = -1;
  // Load dữ liệu từ file JSON
  function loadStudents() {
    const storedStudents = localStorage.getItem("students");
    if (storedStudents) {
      students = JSON.parse(storedStudents);
      renderTable(students);
    } else {
      // If no data in localStorage, load from JSON file (you can adjust this as needed)
      $.getJSON("./students.json", function (data) {
        students = data;
        renderTable(students);
        saveStudents(); // Save loaded data to localStorage
      });
    }
  }
  // Render bảng dữ liệu
  function renderTable(data) {
    const tbody = $("#student-table tbody");
    tbody.empty();
    data.forEach((student, index) => {
      const row = `<tr>
                    <td>${student.name}</td>
                    <td>${student.age}</td>
                    <td>
                      <button class="edit-btn" data-index="${index}">Sửa</button>
                      <button class="delete-btn" data-index="${index}">Xóa</button>
                    </td>
                  </tr>`;
      tbody.append(row);
    });
  }
  // Save students to localStorage
  function saveStudents() {
    localStorage.setItem("students", JSON.stringify(students));
  }
  // Sắp xếp bảng theo tên hoặc tuổi
  function sortTableBy(field) {
    if (field == "name") {
      students.sort((a, b) => {
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
    saveStudents();
    renderTable(students);
  }

  // Tìm kiếm theo tên và bôi đỏ
  $("#search-name-btn").click(function () {
    const searchText = $("#name-search").val().toLowerCase();

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

    if (isNaN(ageFilter) || ageFilter <= 0) {
      $("#name-errorAge").text("Vui lòng nhập giá trị tuổi dương lớn hơn 0.");
      return;
    }
    $("#student-table tbody tr").each(function () {
      const ageCell = parseInt($(this).find("td:last").prev().text(), 10);
      if (ageCell < ageFilter) {
        $(this).hide();
      } else {
        $(this).show();
      }
    });
  });

  // Nút reset
  $("#reset-btn").click(function () {
    saveStudents();
    renderTable(students);
    $("#student-table tbody tr").css("background-color", "").show();
    $("#name-search").val("");
    $("#age-filter").val("");
    $("#name-error").text("");
    $("#name-errorAge").text("");
  });

  // Xóa lỗi khi thay đổi nội dung trong input name
  $("#name-search").on("input", function () {
    $("#name-error").text("");
  });

  // Xóa lỗi khi thay đổi input Age
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

  // Mở modal
  $("#open-modal-btn").click(function () {
    editIndex = -1;
    $("#student-name").val("");
    $("#student-age").val("");
    $("#modal-title").text("Thêm Sinh Viên");
    $("#student-modal").show();
  });

  // Đóng modal
  $(".close").click(function () {
    $("#student-modal").hide();
  });

  // Lưu sinh viên
  $("#student-form").submit(function (event) {
    event.preventDefault();
    const name = $("#student-name").val();
    const age = parseInt($("#student-age").val());

    if (editIndex === -1) {
      students.push({ name, age });
    } else {
      students[editIndex] = { name, age };
    }
    saveStudents();
    renderTable(students);
    $("#student-modal").hide();
  });

  // Sửa sinh viên
  $(document).on("click", ".edit-btn", function () {
    editIndex = $(this).data("index");
    const student = students[editIndex];
    $("#student-name").val(student.name);
    $("#student-age").val(student.age);
    $("#modal-title").text("Sửa Sinh Viên");
    $("#student-modal").show();
  });
  // Xóa sinh viên
  $(document).on("click", ".delete-btn", function () {
    const index = $(this).data("index");
    students.splice(index, 1);
    saveStudents();
    renderTable(students);
  });

  // Load dữ liệu ban đầu
  loadStudents();
});
