<!DOCTYPE html>
<html>
<head>
<title>WattDepot Administration</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link rel="stylesheet" href="/webroot/dist/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="/webroot/dist/css/bootstrap-theme.min.css">
<link rel="stylesheet/less" type="text/css" href="/webroot/dist/css/style.less">
<script src="/webroot/dist/js/less-1.3.0.min.js"></script>
</head>
<body>
    <div class="container">
        <div class="row row-offcanvas row-offcanvas-left">
            <div class="col-xs-6 col-sm-3">
    <table class="table">
      <thead>
        <tr>
          <th>First Name</th>
          <th>Last Name</th>
          <th>Username</th>
          <th>Email</th>
          <th style="width: 36px;"></th>
        </tr>
      </thead>
                    <tbody>
                    <#list users as u>
    <tr><td>${u.firstName!}</td><td>${u.lastName!}</td><td>${u.id}</td><td>${u.email!}</td>
              <td>
              <a href="user.html"><span class="glyphicon glyphicon-pencil"></span></a>
              <a href="#myModal" role="button" data-toggle="modal"><span class="glyphicon glyphicon-remove"></span></a>
          </td>
    </tr>
                    </#list>
                    </tbody>
                </table>
                <form name="new_user">
                    id: <input type="text" name="id"><br>
                    first name: <input type="text" name="firstname"><br>
                    last name: <input type="text" name="lastname"><br>
                    password: <input type="password" name="password"><br>
                    email: <input type="email" name="email"><br> 
                </form>
                <button class="btn btn-primary" onclick="putNewUser();">Add User</button>                
            </div>
        </div>
    </div>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="//code.jquery.com/jquery.js"></script>
    <script>
    function putNewUser() {
        var id = $("input[name='id']").val();
        var first = $("input[name='firstname']").val();
        var last = $("input[name='lastname']").val();
        var pass = $("input[name='password']").val();
        var email = $("input[name='email']").val();
        var usr = {"id": id, "firstName": first, "lastName": last, "email": email, "password": pass, "admin": "false", "properties": []};
        $.ajax({
            url: '/wattdepot/admin/user/temp',
            type: 'PUT',
            contentType:'application/json',
            data: JSON.stringify(usr),
            success: function() {
            location.reload();
            },
         });
        
    };
    </script>
</body>
</html>