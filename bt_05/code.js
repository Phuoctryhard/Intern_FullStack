//ADD
var data02 = [
  {
    ID: "1",
    name: "An An",
    age: 15,
    mon: [
      { tenmon: "Toan", diem: 15 },
      { tenmon: "Van", diem: 10 },
      { tenmon: "Tieng Anh", diem: 9 },
    ],
  },
  {
    ID: "2",
    name: "Bao",
    age: 16,
    mon: [
      { tenmon: "Van", diem: 9 },
      { tenmon: "Toan", diem: 10 },
      { tenmon: "Tieng Anh", diem: 9 },
    ],
  },
];
//them ham
Handlebars.registerHelper("select", function (value, options) {
  var $el = $("<select />").html(options.fn(this));
  $el.find('[value="' + value + '"]').attr({ selected: "selected" });
  return $el.html();
});
//load template with data

var loadTmplAndShow = function (tmplPath, data, divToShow) {
  $.get(tmplPath, function (html) {
    var tmpl = Handlebars.compile(html);
    var rs = tmpl(data);
    showContent(divToShow, rs);
  });
};

//add html content to div
var showContent = function (div, content) {
  $(div).html(content);
  $(document).ready(function () {
    //---after show, we can see btn OK, then define event for this btn
    $("#btn_show")
      .off("click")
      .on("click", function (e) {
        var data = req_gl_data({
          dataZoneDom: $("#div_01"),
        });
        if (data.hasError == false) {
          // thêm id
          data.data.ID = data02.length + 1;
          data02.push(data.data);
          loadTmplAndShow("./tmpl02.html", data02, "#div_02");
        }
      });
  });
};
var data01 = {
  name: "Alan",
  age: 21,
  mon: [
    { tenmon: "Toan", diem: 15 },
    { tenmon: "Van", diem: 10 },
    { tenmon: "Tieng Anh", diem: 9 },
  ],
};
loadTmplAndShow("./tmpl02.html", data02, "#div_02");
loadTmplAndShow("./tmpl01.html", data01, "#div_01");
