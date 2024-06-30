//----Define some function in Handlebars-------------------------------------------------------------------------
Handlebars.registerHelper('uppercase', function(str) {
  var result = str.toUpperCase();   
  return new Handlebars.SafeString(result);
});

Handlebars.registerHelper('select', function(value, options) {
  var $el = $('<select />').html(options.fn(this));
  $el.find('[value="' + value + '"]').attr({'selected':'selected'});
  return $el.html();
});

Handlebars.registerHelper('transl', function(i18n_key) {
  var result = $.i18n(i18n_key);   
  return new Handlebars.SafeString(result);
});

//-----Load language translation---------------------------------------------------------------------
$.i18n({
  locale: 'vn-vi'
});     
$.i18n().load('./transl', 'vi', '1.0').done(function () {
  //--do something 
  loadTmplAndShow ("./tmpl/tmpl01.html", dataAcc, "#cont");
});

//------------------------------------------------------------------
//load template with data
var loadTmplAndShow = function(tmplPath, data, divToShow) {
  console.log("Loading template from:", tmplPath);
  $.get(tmplPath, function(html) {
      console.log("Template loaded, compiling...");
      var tmpl = Handlebars.compile(html);
      var rs = tmpl(data);
      console.log("Template compiled, rendering content...");
      do_gl_showContent(divToShow, rs);
  });
}

//add html content to div
var do_gl_showContent = function(div, content) {
  console.log("Adding content to div:", div);
  $(div).html(content);     
  console.log("Content added, setting up button event handler...");
  //---after show content, we can see btn_send, then define event for this btn
  $("#btn_send").off("click").on("click", function(e) {
      console.log("Button clicked, reading data...");
      //---- read data from div #cont-----
      var dataForm = req_gl_data({
          dataZoneDom: $("#cont")
      });

      if(dataForm.hasError == true) {
          alert("Something wrong");
          return;
      }   
      
      var ref = req_gl_Request_Content_Send("ServiceAutUser", "SvNew", dataForm.data);
      console.log("Sending AJAX request with data:", ref);
      do_gl_ajax("/bo/api/publ", null, ref, 1000, function(res) {
          console.log("AJAX request succeeded:", res);
      }, function(res, statut, erreur) {
          console.log("AJAX request failed:", res, statut, erreur);
      });
  });
}

var req_gl_Request_Content_Send = function(serviceClass, serviceName, data) {
  let ref = {
      'sv_class': serviceClass, 
      'sv_name': serviceName,
  };
  if (data) ref.obj = data;
  console.log("Request content to send:", ref);
  return ref;
}

var do_gl_ajax = function(urlAPI, header, data, timeWaitMax, fSucces, fError) {  
  if (timeWaitMax <= 0) timeWaitMax = 1000*60*60; //1h
  if (typeof data === 'string' || data instanceof String) data = data.split("null").join("");
  
  console.log("Performing AJAX request to:", urlAPI, "with data:", data);
  $.ajax({
      type: 'POST',
      contentType: "application/json",
      url: urlAPI,
      timeout: timeWaitMax,
      data: JSON.stringify(data),
      headers: header,
      success: function(res, statut) {
          console.log("AJAX success response:", res);
          try {
              var resJson = JSON.parse(res);
              if (fSucces) fSucces(resJson);
          } catch(e) {
              console.error("Error parsing AJAX success response:", e);
          }
      },
      error: function(res, statut, erreur) {
          console.error("AJAX error response:", res, statut, erreur);
          if (fError) fError.apply(res, statut, erreur);
      },
      complete: function(res, statut) {
          console.log("AJAX request complete:", res, statut);
      }
  });
};

//--------------------------------------------------------------------------------------------------
//---doPing Server----------------------------------------------------------------------------------
//var ref = req_gl_Request_Content_Send("ServiceAutUser", "SvDel");
//ref.id = 10;
var ref = req_gl_Request_Content_Send("ServiceAutUser", "SvMod");
ref.obj = {id: 10, login: 'abc', pass: '123'};

//var ref = req_gl_Request_Content_Send("ServiceAutUser", "SvPing");
var fSucess = function(res) {
  var msg = res.res_data;
  console.log(msg);
  $("#ping01").html(msg);
  $("#ping02").html(decodeURIComponent(msg));
}
console.log("Sending AJAX request for ping test with data:", ref);
do_gl_ajax("/bo/api/publ", null, ref, 1000, fSucess, null);
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
var dataAcc = {
  "login": "hnv", 
  "pass": "!!!!!!",
  "inf01": "hnv@hnv.com",
  "inf02": "012345",
};
console.log("Data account for testing:", dataAcc);
//loadTmplAndShow ("./tmpl01.html", dataAcc, "#cont");