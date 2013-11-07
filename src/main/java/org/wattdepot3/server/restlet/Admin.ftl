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
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://code.jquery.com/jquery.js"></script>
<script src="/webroot/dist/js/bootstrap.min.js"></script>
<script src="/webroot/dist/js/wattdepot-admin.js"></script>
<script> 
$(function(){
  $("#modal-dialogs").load("/webroot/dist/dialogs.html"); 
  //$("#navigation-bar").load("/webroot/dist/navbar.html");
});
</script> 

</head>
<body>
<nav class="navbar navbar-default" role="navigation">
  <!-- Brand and toggle get grouped for better mobile display -->
  <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
      <span class="sr-only">Toggle navigation</span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    <a class="navbar-brand" href="#"><img src="/webroot/dist/wattdepot-logo.png"> WattDepot: <strong><em>${groupId}</em></strong></a>
  </div>

  <!-- Collect the nav links, forms, and other content for toggling -->
  <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
    <ul class="nav navbar-nav">
      <li class="active"><a href="#">Definitions</a></li>
      <li><a href="/wattdepot/admin/summary/">Logs/Information/Summary</a></li>
 <!--      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li><a href="#">Action</a></li>
          <li><a href="#">Another action</a></li>
          <li><a href="#">Something else here</a></li>
          <li class="divider"></li>
          <li><a href="#">Separated link</a></li>
          <li class="divider"></li>
          <li><a href="#">One more separated link</a></li>
        </ul>
      </li> -->
    </ul>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="#">${groupId}</a></li>
<!--       <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li><a href="#">Action</a></li>
          <li><a href="#">Another action</a></li>
          <li><a href="#">Something else here</a></li>
          <li class="divider"></li>
          <li><a href="#">Separated link</a></li>
        </ul>
      </li> -->
    </ul>
  </div><!-- /.navbar-collapse -->
</nav>

  <div id="modal-dialogs"></div>
  <div class="container">
  <!-- Nav tabs -->
    <ul class="nav nav-tabs">
        <#if groupId == "admin">
        <li><a id="users_tab_link" href="#users" data-toggle="tab">Users</a></li>
        </#if>
        <li><a id="depositories_tab_link" href="#depositories" data-toggle="tab">Depositories</a></li>
        <li><a id="sensors_tab_link" href="#sensors" data-toggle="tab">Sensors</a></li>
        <li><a id="sensorgroups_tab_link" href="#sensorgroups" data-toggle="tab">Sensor Groups</a></li>
        <li><a id="sensorprocesses_tab_link" href="#sensorprocesses" data-toggle="tab">Sensor Process Metadata</a></li>
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
                            <th style="width: 7px;"></th>
                            <th style="width: 7px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list users as u>
                        <tr><td>${u.firstName!}</td><td>${u.lastName!}</td><td>${u.id}</td><td>${u.email!}</td>
                            <td>
                                <#if ! u.admin><span class="glyphicon glyphicon-pencil" onclick="edit_user_dialog(event, '${u.id}');"></span></#if>
                            </td>
                            <td>
                                <#if ! u.admin><span class="glyphicon glyphicon-remove" onclick="delete_user_dialog(event, '${u.id}');"></span></#if>
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
                            <th colspan="2"><h3>Groups</h3></th>                            
                            <th style="width: 7px;"></th>
                            <th style="width: 7px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list groups as g>
                        <tr><td>${g.id}</td><td><#list g.users as u>${u.id} </#list></td>
                            <td>
                                <#if g.id != "admin"><span class="glyphicon glyphicon-pencil" onclick="edit_usergroup_dialog(event, '${g.id}');"></span></#if>
                            </td>
                            <td>
                                <#if g.id != "admin"><span class="glyphicon glyphicon-remove" onclick="delete_usergroup_dialog(event, '${g.id}');"></span></#if>
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
                            <#if groupId == "admin">
                            <th>Owner</th>
                            </#if>
                            <th style="width: 7px;"></th>
                            <th style="width: 7px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list depositories as d>
                        <tr><td>${d.name}</td><td>${d.measurementType}</td><#if groupId == "admin"><td>${d.owner.id}</td></#if>
                            <td>
                                <span class="glyphicon glyphicon-pencil" onclick="edit_depository_dialog(event, '${d.name}');"></span>
                            </td>
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
                            <#if groupId == "admin">
                            <th>Owner</th>
                            </#if>
                            <th style="width: 7px;"></th>
                            <th style="width: 7px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list sensors as s>
                        <tr><td>${s.id}</td><td>${s.uri}</td><td>${s.location.id}</td><td>${s.model.id}</td><#if groupId == "admin"><td>${s.owner.id}</td></#if>
                            <td>
                                <span class="glyphicon glyphicon-pencil" onclick="edit_sensor_dialog(event, '${s.id}');"></span>
                            </td>
                            <td>
                                <span class="glyphicon glyphicon-remove" onclick="delete_sensor_dialog(event, '${s.id}');"></span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <button data-toggle="modal" data-target="#addSensorModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add Sensor</button>
            </div>   
            <div class="panel-group" id="accordion">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseLocation">Sensor Locations</a>
                        </h4>
                    </div>
                    <div id="collapseLocation" class="panel-collapse collapse">
                        <div class="panel-body">
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
                                            <#if groupId == "admin">
                                            <th>Owner</th>
                                            </#if>
                                            <th style="width: 7px;"></th>
                                            <th style="width: 7px;"></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <#list locations as l>
                                        <tr><td>${l.id}</td><td>${l.latitude}</td><td>${l.longitude}</td><td>${l.altitude}</td><td>${l.description}</td><#if groupId == "admin"><td>${l.owner.id}</td></#if>
                                            <td>
                                                <span class="glyphicon glyphicon-pencil" onclick="edit_location_dialog(event, '${l.id}');"></span>
                                            </td>
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
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseModel">Sensor Models</a>
                        </h4>
                    </div>
                    <div id="collapseModel" class="panel-collapse collapse">
                        <div class="well">
                            <table class="table">
                                <thead>
                                    <tr><th colspan="5"><h3>Sensor Models</h3></th></tr>
                                    <tr>
                                        <th>Name</th>
                                        <th>Protocol</th>
                                        <th>Type</th>
                                        <th>Version</th>
                                        <#if groupId == "admin">
                                        <th>Owner</th>
                                        </#if>
                                        <th style="width: 7px;"></th>
                                        <th style="width: 7px;"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <#list sensormodels as m>
                                    <tr><td>${m.id}</td><td>${m.protocol}</td><td>${m.type}</td><td>${m.version}</td><#if groupId == "admin"><td>${m.owner.id}</td></#if>
                                        <td>
                                            <span class="glyphicon glyphicon-pencil" onclick="edit_model_dialog(event, '${m.id}');"></span>
                                        </td>
                                        <td>
                                            <span class="glyphicon glyphicon-remove" onclick="delete_model_dialog(event, '${m.id}');"></span>
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                            <button data-toggle="modal" data-target="#addModelModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add Sensor Model</button>
                        </div>       
                    </div>
                </div>
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
                            <#if groupId == "admin">
                            <th>Owner</th>
                            </#if>
                            <th style="width: 7px;"></th>
                            <th style="width: 7px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list sensorgroups as g>
                        <tr><td>${g.id}</td><td><#list g.sensors as u>${u.id} </#list></td><#if groupId == "admin"><td>${g.owner.id}</td></#if>
                            <td>
                                <span class="glyphicon glyphicon-pencil" onclick="edit_sensorgroup_dialog(event, '${g.id}');"></span>
                            </td>
                            <td>
                                <span class="glyphicon glyphicon-remove" onclick="delete_sensorgroup_dialog(event, '${g.id}');"></span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <button data-toggle="modal" data-target="#addSensorGroupModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add Sensor Group</button>
            </div>       
        </div>
        <div class="tab-pane" id="sensorprocesses">
            <div class="well">
                <table class="table">
                    <thead>
                      <tr><th colspan="5"><h3>Sensor Process Metadata</h3></th></tr>
                        <tr>
                            <th>Name</th>
                            <th>Sensor</th>
                            <th>Polling Interval</th>
                            <th>Depository</th>
                            <#if groupId == "admin">
                            <th>Owner</th>
                            </#if>
                            <th style="width: 7px;"></th>
                            <th style="width: 7px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <#list sensorprocesses as p>
                        <tr><td>${p.id}</td><td>${p.sensor.id}</td><td>${p.pollingInterval}</td><td>${p.depositoryId}</td><#if groupId == "admin"><td>${p.owner.id}</td></#if>
                            <td>
                                <span class="glyphicon glyphicon-pencil" onclick="edit_process_dialog(event, '${p.id}');"></span>
                            </td>
                            <td>
                                <span class="glyphicon glyphicon-remove" onclick="delete_process_dialog(event, '${p.id}');"></span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <button data-toggle="modal" data-target="#addProcessModal" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> Add Sensor Process Metadata</button>
            </div>       
        </div>
    </div>  
    
    
  <!-- Add User Group -->
  <div class="modal fade" id="addUserGroupModal" tabindex="-1" role="dialog" aria-labelledby="addUserGroupModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Add/Edit User Group</h4>
        </div>
        <div class="modal-body">
            <div class="container">
                <form>
                <div class="form-group">
                        <label class="col-md-3 control-label" for="usergroup_name">Group Name</label>
                        <div class="col-md-9">
                            <input type="text" name="usergroup_name" class="form-control"/>
                            <p class="help-block">User group names must be unique.</p>
                        </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label" for="groupusers">Users</label>
                    <div class="col-md-9">
                        <select class="form-control" name="groupusers" multiple="multiple">
                        <#list users as u>
                            <option value="${u.id}">${u.id}</option>
                        </#list>
                        </select>
                        <p class="help-block">Choose the members of the group. Users can only be in one group.</p>
                    </div>
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

  <!-- Add Sensor -->
  <div class="modal fade" id="addSensorModal" tabindex="-1" role="dialog" aria-labelledby="addSensorModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Add/Edit Sensor</h4>
        </div>
        <div class="modal-body">
            <div class="container">
                <form>
                <div class="form-group">
                        <label class="col-md-3 control-label" for="sensor_id">Sensor Name</label>
                        <div class="col-md-9">
                            <input type="text" name="sensor_id" class="form-control">
                            <p class="help-block">Sensor names must be unique.</p>
                        </div>
                </div>
                <div class="form-group">
                        <label class="col-md-3 control-label" for="sensor_uri">Sensor URI</label>
                        <div class="col-md-9">
                            <input type="text" name="sensor_uri" class="form-control">
                            <p class="help-block">The URI to contact the sensor.</p>
                        </div>
                </div>
                <div class="form-group">
                            <label class="col-md-3 control-label" for="sensor_location">Location</label>
                            <div class="col-md-9">
                                <select class="form-control" name="sensor_location">
                                <#list locations as l>
                                    <option value="${l.id}">${l.id}</option>
                                </#list>
                                </select>
                                <p class="help-block">Select the optional location for the sensor.</p>
                            </div>
                </div>
                <div class="form-group">
                            <label class="col-md-3 control-label" for="sensor_model">Model</label>
                            <div class="col-md-9">
                                <select class="form-control" name="sensor_model">
                                <#list sensormodels as sm>
                                    <option value="${sm.id}">${sm.id}</option>
                                </#list>
                                </select>
                                <p class="help-block">Select the model for the sensor.</p>
                            </div>
                </div>
                <div class="clearfix"></div>
                </form>
                <button class="btn-xs btn-success" data-toggle="collapse" data-target="#newLocationForm"><span class="glyphicon glyphicon-plus"></span> Location</button>                
                <div id="newLocationForm" class="collapse"> 
                    <form>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Location Name</label>
                            <div class="col-md-9">
                                <input type="text" name="inline_location_id" class="form-control">
                                <p class="help-block">The unique name of the location.</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Latitude</label>
                            <div class="col-md-9">
                            <input type="number"
                                name="inline_location_latitude"
                                class="form-control">
                            <p class="help-block">Enter the latitude.</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Longitude</label>
                            <div class="col-md-9">
                            <input type="number"
                                name="inline_location_longitude"
                                class="form-control">
                            <p class="help-block"></p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Altitude</label>
                            <div class="col-md-9">
                            <input type="number"
                                name="inline_location_altitude"
                                class="form-control">
                            <p class="help-block"></p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Description</label>
                            <div class="col-md-9">
                            <input type="text"
                                name="inline_location_description"
                                class="form-control">
                            <p class="help-block"></p>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </form>
                    <button type="button" class="btn-sm btn-primary"
                        onclick="putNewInlineLocation();">Save Location</button>
                    <p></p>
                </div>
                <button class="btn-xs btn-success" data-toggle="collapse" data-target="#newModelForm"><span class="glyphicon glyphicon-plus"></span> Model</button>
                <div id="newModelForm" class="collapse"> 
                    <form>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Sensor Model Name</label> 
                            <div class="col-md-9">
                                <input type="text"
                                name="inline_model_id" class="form-control">
                                <p class="help-block">Unique model name.</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Protocol</label>
                            <div class="col-md-9">
                            <input type="text" name="inline_model_protocol"
                                class="form-control">
                            <p class="help-block">The protocol used by the sensor.</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Type</label>
                            <div class="col-md-9">
                            <input type="text" name="inline_model_type"
                                class="form-control">
                            <p class="help-block">The type of the sensor.</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Version</label>
                            <div class="col-md-9">
                            <input type="text" name="inline_model_version"
                                class="form-control">
                            <p class="help-block">The version.</p>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </form>
                    <button type="button" class="btn-sm btn-primary"
                        onclick="putNewInlineModel();">Save Model</button>
                    <p></p>
                </div>                
                
            </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" onclick="putNewSensor();">Save changes</button>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->    

  <!-- Add Sensor Group -->
  <div class="modal fade" id="addSensorGroupModal" tabindex="-1" role="dialog" aria-labelledby="addSensorGroupModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Add/Edit Sensor Group</h4>
        </div>
        <div class="modal-body">
            <div class="container">
                <form>
                <div class="form-group">
                        <label class="col-md-3 control-label" for="sensorgroup_name">Group Name</label>
                        <div class="col-md-9">
                        <input type="text" name="sensorgroup_name" class="form-control"><p class="help-block"></p>
                        </div>
                </div>
                <div class="form-group">
                            <label class="col-md-3 control-label" for="groupsensors">Sensors</label>
                            <div class="col-md-9">
                            <select class="form-control" name="groupsensors" multiple="multiple">
                            <#list sensors as s>
                                <option value="${s.id}">${s.id}</option>
                            </#list>
                            </select>
                            <p class="help-block"></p>
                            </div>
                </div>
                <div class="clearfix"></div>
                </form>
            </div>                
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" onclick="putNewSensorGroup();">Save changes</button>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->    

  <!-- Add Sensor Process -->
  <div class="modal fade" id="addProcessModal" tabindex="-1" role="dialog" aria-labelledby="addProcessModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          <h4 class="modal-title">Add/Edit Sensor Process</h4>
        </div>
        <div class="modal-body">
            <div class="container">
                <form>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Process Name</label>
                        <div class="col-sm-9">
                            <input class="form-control" type="text" name="sensorprocess_name" class="form-control">
                            <p class="help-block">Unique name for the metadata.</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Sensor</label>
                        <div class="col-sm-9">
                            <select class="form-control" name="process_sensor">
                            <#list sensors as s>
                                <option value="${s.id}">${s.id}</option>
                            </#list>
                            </select>
                            <p class="help-block">Select the sensor making measurements.</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Polling Interval</label>
                        <div class="col-sm-9">
                            <input class="form-control" type="number" name="sensorprocess_polling" class="form-control">
                            <p class="help-block">Number of seconds between measurements.</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">Depository</label>
                        <div class="col-sm-9">
                            <select class="form-control" name="process_depository">
                            <#list depositories as d>
                                <option value="${d.name}">${d.name}</option>
                            </#list>
                            </select>
                            <p class="help-block">Select the depository to store the measurements.</p>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </form>
            </div>
        </div>                
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary" onclick="putNewProcess();">Save changes</button>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->    

</div>
<script>
$(document).ready(function () {
    var selected_tab = getCookie("selected-tab");
    if (selected_tab != null) {
        $('#' + selected_tab + '_tab_link').tab('show');
    }
    
});

var GROUPID = "${groupId}";
var USERS = {};
<#list users as u>
USERS["${u.id}"] = {"id": "${u.id}", "firstName" : "${u.firstName!"none"}", "lastName" : "${u.lastName!"none"}", "email" : "${u.email!"none"}", "password" : "${u.password!"none"}", "admin" : <#if u.admin>true<#else>false</#if>, "properties" : [<#assign k = u.properties?size><#list u.properties as p>{"key":"${p.key}", "value":"${p.value}"}<#if k != 1>,</#if><#assign k = k -1></#list>]};
</#list>
var USERGROUPS = {};
<#list groups as g>
USERGROUPS["${g.id}"] = {"id": "${g.id}", "users": [
<#assign j = g.users?size>
<#list g.users as u>
{"id": "${u.id}", "firstName" : "${u.firstName!"none"}", "lastName" : "${u.lastName!"none"}", "email" : "${u.email!"none"}", "password" : "${u.password!"none"}", "admin" : <#if u.admin>true<#else>false</#if>, "properties" : [<#assign k = u.properties?size><#list u.properties as p>{"key":"${p.key}", "value":"${p.value}"}<#if k != 1>,</#if><#assign k = k -1></#list>]}<#if j != 1>,</#if><#assign j = j - 1>
</#list>
]};
</#list>
var DEPOSITORIES = {};
<#list depositories as d>
DEPOSITORIES["${d.name}"] = {"name": "${d.name}", "measurementType": "${d.measurementType}", "ownerId": "${d.owner.id}"};
</#list>
var LOCATIONS = {};
<#list locations as l>
LOCATIONS["${l.id}"] = {"id": "${l.id}", "latitude": ${l.latitude}, "longitude": ${l.longitude}, "altitude": ${l.altitude}, "description": "${l.description}", "ownerId": "${l.owner.id}"};
</#list>
var MODELS = {};
<#list sensormodels as m>
MODELS["${m.id}"] = {"id": "${m.id}", "protocol": "${m.protocol}", "type": "${m.type}", "version": "${m.version}", "ownerId": "${m.owner.id}"};
</#list>
var SENSORS = {};
<#list sensors as s>
SENSORS["${s.id}"] = {"id": "${s.id}", "uri": "${s.uri}", "locationId": "${s.location.id}", "modelId": "${s.model.id}", "ownerId": "${s.owner.id}"};
</#list>
var SENSORGROUPS = {};
<#list sensorgroups as sg>
SENSORGROUPS["${sg.id}"] = {"id": "${sg.id}", "sensors": [
<#assign sgLen = sg.sensors?size>
<#list sg.sensors as s>
{"id": "${s.id}"}<#if sgLen != 1>,</#if><#assign sgLen = sgLen - 1>
</#list>
], "ownerId": "${sg.owner.id}"};
</#list>
var SENSORPROCESSES = {};
<#list sensorprocesses as sp>
SENSORPROCESSES["${sp.id}"] = {"id": "${sp.id}", "sensorId": "${sp.sensor.id}", "pollingInterval": ${sp.pollingInterval}, "depositoryId": "${sp.depositoryId}", "ownerId": "${sp.owner.id}"};
</#list>
</script>
</body>
</html>