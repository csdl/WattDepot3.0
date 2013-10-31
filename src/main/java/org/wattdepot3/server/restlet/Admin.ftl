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
  <!-- Nav tabs -->
    <ul class="nav nav-tabs">
        <#if groupId == "admin">
        <li><a href="#users" data-toggle="tab">Users</a></li>
        </#if>
        <li><a href="#depositories" data-toggle="tab">Depositories</a></li>
        <li><a href="#locations" data-toggle="tab">Locations</a></li>
        <li><a href="#sensors" data-toggle="tab">Sensors</a></li>
        <li><a href="#sensorgroups" data-toggle="tab">Sensor Groups</a></li>
        <li><a href="#sensormodels" data-toggle="tab">Sensor Models</a></li>
        <li><a href="#sensorprocesses" data-toggle="tab">Sensor Processes</a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
        <div class="tab-pane <#if groupId == "admin">active</#if>" id="users">
            <div class="well">
                <table class="table">
                    <thead>
                      <tr><th colspan="5"><h3>Users</h3></th></tr>
                        <tr>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Username</th>
                            <th>Email</th>
                            <th>Admin?</th>
                            <th style="width: 15px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list users as u>
                        <tr><td>${u.firstName!}</td><td>${u.lastName!}</td><td>${u.id}</td><td>${u.email!}</td><td><#if u.admin>Yes</#if></td>
                            <td>
                                <span class="glyphicon glyphicon-remove" onclick="delete_dialog(event, '${u.id}');"></span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <a data-toggle="modal" href="#addUserModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add User</a>
            </div>       
            <div class="well">
                <table class="table">
                    <thead>
                        <tr>
                            <th colspan="2"><h3>Groups</h3></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list groups as g>
                        <tr><td>${g.id}</td><td><#list g.users as u>${u.id} </#list></td>
                    </#list>
                    </tbody>
                </table>
            </div>        
        </div>
        <div class="tab-pane <#if groupId != "admin">active</#if>" id="depositories">
            <div class="well">
                <table class="table">
                    <thead>
                      <tr><th colspan="5"><h3>Depositories</h3></th></tr>
                        <tr>
                            <th>Name</th>
                            <th>Measurement Type</th>
                            <th style="width: 15px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list depositories as d>
                        <tr><td>${d.name}</td><td>${d.measurementType}</td>
                            <td>
                                <span class="glyphicon glyphicon-remove" onclick="delete_dialog(event, '${u.id}');"></span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <a data-toggle="modal" href="#depsitoryModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add Depository</a>
            </div>       
        </div>
        <div class="tab-pane" id="locations">...</div>
        <div class="tab-pane" id="sensors">...</div>
        <div class="tab-pane" id="sensorgroups">...</div>
        <div class="tab-pane" id="sensormodels">...</div>
        <div class="tab-pane" id="sensorprocesses">...</div>
    </div>  
  <!-- Modal -->
  <div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="addUserModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Add User</h4>
        </div>
        <div class="modal-body">
            <div class="container">
                <div class="row">
                    <form>
                        <label class="col-md-3 col-md-offset-1">First Name</label>
                        <input type="text" name="firstname" class="form-inline"><p></p>
                </div>
                <div class="row">
                            <label class="col-md-3 col-md-offset-1">Last Name</label>
                            <input type="text" name="lastname" class="form-inline"><p></p>
                </div>
                <div class="row">
                            <label class="col-md-3 col-md-offset-1">Email Address</label>
                            <input type="email" name="email" class="form-inline"><p></p>
                </div>
                <div class="row">
                            <label class="col-md-3 col-md-offset-1">Username</label>
                            <input type="text" name="id" class="form-inline"><p></p>
                </div>
                <div class="row">
                            <label class="col-md-3 col-md-offset-1">Password</label>
                            <input type="password" name="password" class="form-inline"><p></p>
                </div>
                <div class="row">
                 <label class="col-md-3 col-md-offset-1"><input type="checkbox" name="admin"> is an admin?</label>
                 
                            <div class="clearfix"></div>
                        </form>
                </div>
            </div>                
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" onclick="putNewUser();">Save changes</button>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->    
  
  <div class="modal fade" id="deleteUserModal" tabindex="-1" role="dialog" aria-labelledby="deleteUserModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">Delete User</h4>
        </div>
        <div class="modal-body" id="modal-body">
            <p><b>Delete User </b></p>
            <div id="user_id">
            </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button id="delete_button" type="button" class="btn btn-primary" onclick="deleteUser();">Delete User</button>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
       
        </div>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="/webroot/dist/js/bootstrap.min.js"></script>
    <script>
    function putNewUser() {
        var id = $("input[name='id']").val();
        var first = $("input[name='firstname']").val();
        var last = $("input[name='lastname']").val();
        var pass = $("input[name='password']").val();
        var email = $("input[name='email']").val();
        var admin = $("input[name='admin']").is(':checked');
        var usr = {"id": id, "firstName": first, "lastName": last, "email": email, "password": pass, "admin": "false", "properties": []};
        if (admin) {
            usr['admin'] = "true";
        }
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
    
    function delete_dialog(event, id) {
        var modalElement = $('#deleteUserModal');
           
        modalElement.modal({
            backdrop: true,
            keyboard: true,
            show: false
        }); 
        modalElement.find('#user_id').html(id);
   //     modalElement.find('#delete_button').onclick = deleteUser(id);
        modalElement.modal('show');
    };
    
    function deleteUser() {
        var id = $('#user_id').html();
        console.log(id);
        $.ajax({
            url: '/wattdepot/admin/user/' + id,
            type: 'DELETE',
            contentType:'application/json',
            success: function() {
            location.reload();
            },
         });
    
    };
    </script>
</body>
</html>