
<!-- Begin image upload -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <button id="addImageButton" type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#addImageForm">Add Image</button>
  <div id="addImageForm" class="modal fade" role="dialog">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
      	  <h4 class="modal-title">Add Image</h4>
        </div>
        <form method="POST" action="addImage.do" enctype="multipart/form-data">
          <div class="modal-body">
            <div id="productUpc" class="form-group">
              <input type="hidden" name="upc" value="${param.upc}">
			</div>
			<div id="imageFileUpload" class="form-group">
  			  <input type="file" name="file" id="fileChooser" /><br />
<!--   			  Name: <input type="text" name="name"><br /><br /> -->
  			  <input type="submit" name="upload" value="Upload" />
			</div>
          </div>
          <div class="modal-footer">
          	<div class="form-group">
          	  <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
			</div>
          </div>
        </form>
      </div>
    </div>
  </div>
<!-- End image upload -->
