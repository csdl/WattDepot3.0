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
                                <span class="glyphicon glyphicon-remove" onclick="delete_user_dialog(event, '${u.id}');"></span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <button data-toggle="modal" data-target="#addUserModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add User</button>
            </div>       
            <div class="well">
                <table class="table">
                    <thead>
                        <tr>
                            <th colspan="2"><h3>Groups</h3></th><th style="width: 15px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list groups as g>
                        <tr><td>${g.id}</td><td><#list g.users as u>${u.id} </#list></td><td>
                                <span class="glyphicon glyphicon-remove" onclick="delete_group_dialog(event, '${g.id}');"></span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <button data-toggle="modal" data-target="#addUserGroupModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add User Group</button>
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
                                <span class="glyphicon glyphicon-remove" onclick="delete_depository_dialog(event, '${d.name}');"></span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <button data-toggle="modal" data-target="#addDepositoryModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add Depository</a>
            </div>       
        </div>
        <div class="tab-pane" id="locations">
            <div class="well">
                <table class="table">
                    <thead>
                      <tr><th colspan="5"><h3>Locations</h3></th></tr>
                        <tr>
                            <th>Name</th>
                            <th>Latitude</th>
                            <th>Longitude</th>
                            <th>Altitude</th>
                            <th>Description</th>
                            <th style="width: 15px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list locations as l>
                        <tr><td>${l.id}</td><td>${l.latitude}</td><td>${l.longitude}</td><td>${l.altitude}</td><td>${l.description}</td>
                            <td>
                                <span class="glyphicon glyphicon-remove" onclick="delete_location_dialog(event, '${l.id}');"></span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <button data-toggle="modal" data-target="#addLocationModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add Location</button>
            </div>       
        </div>
        <div class="tab-pane" id="sensors">
            <div class="well">
                <table class="table">
                    <thead>
                      <tr><th colspan="5"><h3>Sensors</h3></th></tr>
                        <tr>
                            <th>Name</th>
                            <th>URI</th>
                            <th>Location</th>
                            <th>Model</th>
                            <th>Properties</th>
                            <th style="width: 15px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list sensors as s>
                        <tr><td>${s.id}</td><td>${s.uri}</td><td>${s.location}</td><td>${s.model}</td><td>${s.properties}</td>
                            <td>
                                <span class="glyphicon glyphicon-remove" onclick="delete_sensor_dialog(event, '${l.id}');"></span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <button data-toggle="modal" data-target="#sensorModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add Sensor</button>
            </div>       
        </div>
        <div class="tab-pane" id="sensorgroups">
            <div class="well">
                <table class="table">
                    <thead>
                      <tr><th colspan="5"><h3>Sensor Groups</h3></th></tr>
                        <tr>
                            <th>Name</th>
                            <th>Sensors</th>
                            <th style="width: 15px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list sensorgroups as g>
                        <tr><td>${g.id}</td><td>${g.sensors}</td>
                            <td>
                                <span class="glyphicon glyphicon-remove" onclick="delete_group_dialog(event, '${g.id}');"></span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <button data-toggle="modal" data-target="#sensorGroupModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add Sensor Group</button>
            </div>       
        </div>
        <div class="tab-pane" id="sensormodels">
            <div class="well">
                <table class="table">
                    <thead>
                      <tr><th colspan="5"><h3>Sensor Models</h3></th></tr>
                        <tr>
                            <th>Name</th>
                            <th>Protocol</th>
                            <th>Type</th>
                            <th>Version</th>
                            <th style="width: 15px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list sensormodels as m>
                        <tr><td>${m.id}</td><td>${m.protocol}</td><td>${m.type}</td><td>${m.version}</td>
                            <td>
                                <span class="glyphicon glyphicon-remove" onclick="delete_model_dialog(event, '${m.id}');"></span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <button data-toggle="modal" data-target="#modelModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add Sensor Model</button>
            </div>       
		</div>
        <div class="tab-pane" id="sensorprocesses">
            <div class="well">
                <table class="table">
                    <thead>
                      <tr><th colspan="5"><h3>Sensor Processes</h3></th></tr>
                        <tr>
                            <th>Name</th>
                            <th>Sensor</th>
                            <th>Polling Interval</th>
                            <th>Depository</th>
                            <th style="width: 15px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list sensorprocesses as p>
                        <tr><td>${p.id}</td><td>${p.sensor.id}</td><td>${p.pollingInterval}</td><td>${p.depositoryId}</td>
                            <td>
                                <span class="glyphicon glyphicon-remove" onclick="delete_process_dialog(event, '${p.id}');"></span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <button data-toggle="modal" data-target="#processModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add Sensor Process</button>
            </div>       
		</div>
    </div>  
  <!-- Modal Dialogs -->
  <!-- Add User -->
  <div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="addUserModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Add User</h4>
        </div>
        <div class="modal-body">
            <div class="container">
                <form>
                <div class="row">
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
                </div>
                </form>
            </div>                
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" onclick="putNewUser();">Save changes</button>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->    

  <!-- Add User Group -->
  <div class="modal fade" id="addUserGroupModal" tabindex="-1" role="dialog" aria-labelledby="addUserGroupModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Add User Group</h4>
        </div>
        <div class="modal-body">
            <div class="container">
                <form>
                <div class="row">
                        <label class="col-md-3 col-md-offset-1">Group Name</label>
                        <input type="text" name="usergroupname" class="form-inline"><p></p>
                </div>
                <div class="row">
                            <label class="col-md-3 col-md-offset-1">Users</label>
                            <select name="groupusers" multiple>
                            <#list users as u>
                            	<option value="${u.id}">${u.id}</option>
                            </#list>
                            </select>
				</div>
                <div class="clearfix"></div>
                </form>
            </div>                
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" onclick="putNewUserGroup();">Save changes</button>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->    

  <!-- Add Depository -->
  <div class="modal fade" id="addDepositoryModal" tabindex="-1" role="dialog" aria-labelledby="addDepositoryModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Add Depository</h4>
        </div>
        <div class="modal-body">
            <div class="container">
                <form>
                <div class="row">
                        <label class="col-md-3 col-md-offset-1">Depository Name</label>
                        <input type="text" name="depositoryname" class="form-inline"><p></p>
                </div>
                <div class="row">
                        <label class="col-md-3 col-md-offset-1">Depository Measurement Type</label>
                        <input type="text" name="depositorytype" class="form-inline"><p></p>
				</div>
                <div class="clearfix"></div>
                </form>
            </div>                
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" onclick="putNewDepository();">Save changes</button>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->    

  <!-- Add Location -->
  <div class="modal fade" id="addLocationModal" tabindex="-1" role="dialog" aria-labelledby="addLocationModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Add Location</h4>
        </div>
        <div class="modal-body">
            <div class="container">
                <form>
                <div class="row">
                        <label class="col-md-3 col-md-offset-1">Location Name</label>
                        <input type="text" name="locationname" class="form-inline"><p></p>
                </div>
                <div class="row">
                        <label class="col-md-3 col-md-offset-1">Latitude</label>
                        <input type="number" name="locationlatitude" class="form-inline"><p></p>
				</div>
                <div class="row">
                        <label class="col-md-3 col-md-offset-1">Longitude</label>
                        <input type="number" name="locationlongitude" class="form-inline"><p></p>
				</div>
                <div class="row">
                        <label class="col-md-3 col-md-offset-1">Altitude</label>
                        <input type="number" name="locationaltitude" class="form-inline"><p></p>
				</div>
                <div class="row">
                        <label class="col-md-3 col-md-offset-1">Description</label>
                        <input type="text" name="locationdescription" class="form-inline"><p></p>
				</div>
                <div class="clearfix"></div>
                </form>
            </div>                
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" onclick="putNewLocation();">Save changes</button>
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
    
    function delete_user_dialog(event, id) {
        var modalElement = $('#deleteUserModal');
           
        modalElement.modal({
            backdrop: true,
            keyboard: true,
            show: false
        }); 
        modalElement.find('#user_id').html(id);
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