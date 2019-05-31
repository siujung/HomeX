$().ready(function() {
  $("#register").validate({
    rules: {
     
      password: {
        required: true,
        minlength: 5
      },
      confirm_password: {
        required: true,
        minlength: 5,
        equalTo: "#password"
      },
      email: {
        required: true,
        email: true
      },
      
    },
    messages: {
     
      password: {
        required: "Please enter password",
        minlength: "Your password must be at least 5 characters long"
      },
      confirm_password: {
        required: "Please enter password",
        minlength: "Your password must be at least 5 characters long ",
        equalTo: "Please enter the same password as abrove"
      },
      email: "Please enter a correct email address"
      
     }
    })
});
