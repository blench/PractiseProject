/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.30
 * Generated at: 2017-08-10 08:12:00 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp.office.application;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.http.HttpSession;
import com.jpsoft.cms.office.entity.User;

public final class detail_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/WEB-INF/tld/c.tld", Long.valueOf(1489643968000L));
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	User user = (User)session.getAttribute("user");
	if(user==null)
	{
      out.write("\r\n");
      out.write("\t\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t\twindow.location = \"");
      out.print(path);
      out.write("/login\";\r\n");
      out.write("\t\t</script>\r\n");
      out.write("\t");
}
	

      out.write('\r');
      out.write('\n');

	String contextPath = request.getContextPath();
	System.out.println("contextPath:"+contextPath);

      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n");
      out.write("\t<title>新闻栏目</title>\r\n");
      out.write("\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/inc/meta.jsp", out, false);
      out.write("\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/js/xheditor/xheditor-1.2.1.min.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(contextPath);
      out.write("/js/xheditor/xheditor_lang/zh-cn.js\"></script>\r\n");
      out.write("\t<script>\r\n");
      out.write("\tfunction validate(){\r\n");
      out.write("\t\treturn $(\"#form1\").form('validate');\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\tfunction uploadPic(){\r\n");
      out.write("\t\tart.dialog.open('");
      out.print(path);
      out.write("/office/supplies/uploadPicPage', \r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\ttitle: '上传图片',\r\n");
      out.write("\t\t\t\t\topacity: 0.87,\t// 透明度,\r\n");
      out.write("\t\t\t\t\twidth:400,\r\n");
      out.write("\t\t\t\t\theight:300,\r\n");
      out.write("\t\t\t\t\tcancel: true\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t);\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t$(document).ready(function(){\r\n");
      out.write("\t\t$(\"#showSelect\").hide();\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\tvar array = new Array();\r\n");
      out.write("\t\r\n");
      out.write("\tfunction getFormData(){\r\n");
      out.write("\t\t$(\"#checkedItems\").val(array);\r\n");
      out.write("\t\tvar data = $(\"#form1\").serializeArray();\r\n");
      out.write("\t\treturn data;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\tfunction select(){\r\n");
      out.write("\t\tdetailDialog = art.dialog.open('");
      out.print(path);
      out.write("/office/application/select?rnd='+Math.random(),\r\n");
      out.write("\t\t{\r\n");
      out.write("\t\t\ttitle:'选择办公用品',\r\n");
      out.write("\t\t\topacity:0.87,\r\n");
      out.write("\t\t\twidth:400,\r\n");
      out.write("\t\t\theight:300,\r\n");
      out.write("\t\t\tok:function()\r\n");
      out.write("\t\t\t{\r\n");
      out.write("\t\t\t\t//得到当前页面的窗口对象，从而调用getSelectRoles()\r\n");
      out.write("\t\t\t\tvar win = this.iframe.contentWindow;\r\n");
      out.write("\t\t\t\tvar checkedItems = new Array();\r\n");
      out.write("\t\t\t\t//注意要记得清除append(),注意但要保存第一项\r\n");
      out.write("\t\t\t\t$(\"#showSelect tr:gt(0)\").remove();\r\n");
      out.write("\t\t\t\t//粗心，少了两个ed应该为checkedItems而我写成了checkItems\r\n");
      out.write("\t\t\t\tcheckedItems = win.getSelectRoles();\r\n");
      out.write("\t\t\t\t$.ajax({\r\n");
      out.write("\t\t\t\t\ttype:\"post\",\r\n");
      out.write("\t\t\t\t\turl:\"");
      out.print(contextPath);
      out.write("/office/application/getselectresult?rnd=\"+Math.random(),\r\n");
      out.write("\t\t\t\t\t//此处应该注意值是以键值对的方式传递的,这是必须的要求，\r\n");
      out.write("\t\t\t\t\tdata:'checkedItems='+checkedItems,\r\n");
      out.write("\t\t\t\t\tasync:false,\r\n");
      out.write("\t\t\t\t\tsuccess:function(json){\r\n");
      out.write("\t\t\t\t\t\tresult = json.result;\r\n");
      out.write("\t\t\t\t\t\tif(result)\r\n");
      out.write("\t\t\t\t\t\t{\r\n");
      out.write("\t\t\t\t\t\t\t$(\"#showSelect\").show();\r\n");
      out.write("\t\t\t\t\t\t\tvar arr = new Array();\r\n");
      out.write("\t\t\t\t\t\t\tarr = json.rows;\r\n");
      out.write("\t\t\t\t\t\t\tfor(var i=0;i<arr.length;i++)\r\n");
      out.write("\t\t\t\t\t\t\t{\r\n");
      out.write("\t\t\t\t\t\t\t\tarray.push(arr[i].id);\r\n");
      out.write("\t\t\t\t\t\t\t\t$(\"#showSelect\").append(\"<tr>\"+\"<td>\"+arr[i].name+\"</td>\"+\"<td>\"+arr[i].brand+\"</td>\"+\"<td>\"+arr[i].provider+\"</td>\"+\"</tr>\");\r\n");
      out.write("\t\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\tdataType:\"json\"\t\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\t},\r\n");
      out.write("\t\tcancel:true\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\tfunction getCheckedResult()\r\n");
      out.write("\t{\r\n");
      out.write("\t\tvar checkedItems = new Array();\r\n");
      out.write("\t\tcheckedItems = getSelectRoles();\r\n");
      out.write("\t\t//alert(checkedItems);\r\n");
      out.write("\t\tvar result = false;\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$.ajax({\r\n");
      out.write("\t\t\ttype:\"post\",\r\n");
      out.write("\t\t\turl:\"");
      out.print(contextPath);
      out.write("/office/application/getselectresult\",\r\n");
      out.write("\t\t\t//此处应该注意值是以键值对的方式传递的,这是必须的要求，\r\n");
      out.write("\t\t\tdata:'checkedItems='+checkedItems,\r\n");
      out.write("\t\t\tasync:false,\r\n");
      out.write("\t\t\tsuccess:function(json){\r\n");
      out.write("\t\t\t\tresult = json.result;\r\n");
      out.write("\t\t\t\tif(result)\r\n");
      out.write("\t\t\t\t{\r\n");
      out.write("\t\t\t\t\tshow(json);\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t},\r\n");
      out.write("\t\t\tdataType:\"json\"\r\n");
      out.write("\t\t});\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<div class=\"easyui-layout\" data-options=\"fit:true,border:false\">\r\n");
      out.write("\t<div data-options=\"region:'center',border:false\" title=\"\" style=\"padding:10px;\">\r\n");
      out.write("\t\t\t<form id=\"form1\" method=\"post\">\r\n");
      out.write("\t\t\t\t<input type=\"hidden\" name=\"id\" value=\"");
      if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
        return;
      out.write("\"/>\r\n");
      out.write("\t\t\t\t<div class=\"tbl_padding\">\r\n");
      out.write("\t\t\t\t<table>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>主题:</td>\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<input type=\"text\" name=\"subject\" class=\"easyui-validatebox\" value=\"");
      if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
        return;
      out.write("\" data-options=\"required:true\"/>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>申请人:</td>\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<input type=\"text\" name=\"applicant\" class=\"easyui-validatebox\" value=\"");
      if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
        return;
      out.write("\"  data-options=\"required:true\"/>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>排序:</td>\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<input type=\"text\" name=\"sortNo\" class=\"easyui-validatebox\" value=\"");
      if (_jspx_meth_c_005fout_005f3(_jspx_page_context))
        return;
      out.write("\"  data-options=\"required:true\"/>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-add',plain:true\" onclick=\"javascript:select()\">选择</a>\r\n");
      out.write("\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t<input type=\"hidden\" name=\"checkedItems\" id=\"checkedItems\"/>\r\n");
      out.write("\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t<table id=\"showSelect\">\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<th>名称</th>\r\n");
      out.write("\t\t\t\t\t\t\t<th>品牌</th>\r\n");
      out.write("\t\t\t\t\t\t\t<th>供应商</th>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</form>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_005fout_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:out
    org.apache.taglibs.standard.tag.rt.core.OutTag _jspx_th_c_005fout_005f0 = (org.apache.taglibs.standard.tag.rt.core.OutTag) _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.OutTag.class);
    _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fout_005f0.setParent(null);
    // /WEB-INF/jsp/office/application/detail.jsp(141,42) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fout_005f0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${officeSupplies.id}", java.lang.Object.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
    int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
    if (_jspx_th_c_005fout_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fout_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:out
    org.apache.taglibs.standard.tag.rt.core.OutTag _jspx_th_c_005fout_005f1 = (org.apache.taglibs.standard.tag.rt.core.OutTag) _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.OutTag.class);
    _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fout_005f1.setParent(null);
    // /WEB-INF/jsp/office/application/detail.jsp(147,74) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fout_005f1.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${officeApplicant.subject}", java.lang.Object.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
    int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
    if (_jspx_th_c_005fout_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
    return false;
  }

  private boolean _jspx_meth_c_005fout_005f2(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:out
    org.apache.taglibs.standard.tag.rt.core.OutTag _jspx_th_c_005fout_005f2 = (org.apache.taglibs.standard.tag.rt.core.OutTag) _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.OutTag.class);
    _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
    _jspx_th_c_005fout_005f2.setParent(null);
    // /WEB-INF/jsp/office/application/detail.jsp(153,76) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fout_005f2.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${officeApplicant.applicant}", java.lang.Object.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
    int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
    if (_jspx_th_c_005fout_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
    return false;
  }

  private boolean _jspx_meth_c_005fout_005f3(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:out
    org.apache.taglibs.standard.tag.rt.core.OutTag _jspx_th_c_005fout_005f3 = (org.apache.taglibs.standard.tag.rt.core.OutTag) _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.OutTag.class);
    _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
    _jspx_th_c_005fout_005f3.setParent(null);
    // /WEB-INF/jsp/office/application/detail.jsp(159,73) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fout_005f3.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${officeApplicant.sortNo}", java.lang.Object.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
    int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
    if (_jspx_th_c_005fout_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
    return false;
  }
}
