/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.75
 * Generated at: 2023-07-05 12:20:35 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.views.user;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class joinTmp_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSP들은 오직 GET, POST 또는 HEAD 메소드만을 허용합니다. Jasper는 OPTIONS 메소드 또한 허용합니다.");
        return;
      }
    }

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

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("<meta charset=\"UTF-8\">\n");
      out.write("<title>Insert title here</title>\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../resources/joinTmp_css.css\">\n");
      out.write("<script src=\"https://code.jquery.com/jquery-3.6.0.min.js\"></script>\n");
      out.write("\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("\n");
      out.write("<div class=\"nav\"> <!--nav  -->\n");
      out.write("</div>\n");
      out.write("<!--========================================================-->\n");
      out.write("\n");
      out.write("<form action=\"/member/signup\" method=\"post\">\n");
      out.write("\n");
      out.write("<div class=\"wrap\">   <!--전체를 감싸는 디브  -->\n");
      out.write("\n");
      out.write("<!--======================== id ============================  -->\n");
      out.write("<div class=\"div1\"> <!--아이디 div  -->\n");
      out.write("	<div class=\"idText\">아이디</div>\n");
      out.write("	<div class=\"idIput\"><input type=\"text\" id=\"id\" name=\"id\" class=\"idArea\" placeholder= \"아이디를 입력해주세요.\"></div>\n");
      out.write("	<p id=\"idMsg\" class=\"idMsg\">영문, 숫자로 조합된 5~15자리여야 합니다.</p>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<!--======================= pw ===========================  --> \n");
      out.write("\n");
      out.write("<div class=\"div2\"> <!-- 비밀번호 div  -->\n");
      out.write("	<div class=\"pwText\">비밀번호</div>\n");
      out.write("	<div class=\"pwInput\"><input type=\"text\" id=\"pw\" name=\"pw\" class=\"pwArea\" placeholder= \"비밀번호를 입력해주세요.\" autocomplete=\"off\">\n");
      out.write("	</div>\n");
      out.write("	<p id=\"pwMsg\" class=\"pwMsg\">영문, 숫자, 특수문자로 조합된 8~20자리여야 합니다. </p>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<!--==========================pw check ========================  -->\n");
      out.write("<div class=\"div3\"> <!--비밀번호 확인 div  -->\n");
      out.write("	<div class=\"pwcText\">재입력</div>\n");
      out.write("	<div class=\"pwcInput\"><input type=\"text\" id=\"pwCheck\" class=\"pwcArea\" placeholder = \"비밀번호를 재입력해주세요.\" autocomplete=\"off\"></div>\n");
      out.write("	<p id=\"pwCheckMsg\" class=\"pwcMsg\">확인용</p>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<!--=========================== nickname =====================  -->\n");
      out.write("\n");
      out.write("<div class=\"div4\"> <!--닉네임  -->\n");
      out.write("	<div class=\"nickText\">닉네임</div>\n");
      out.write("	<div class=\"nickInput\"><input type=\"text\" id=\"nickname\" class=\"nickArea\" name=\"nickname\" placeholder = \"닉네임을 입력해주세요.\"></div>\n");
      out.write("	<p id=\"nicknameMsg\" class=\"nickMsg\">확인용</p>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<!--======================== email ==============================  -->\n");
      out.write("<div class=\"div5\"> <!--이메일 div  -->\n");
      out.write("	<div class=\"emailText\">이메일</div>\n");
      out.write("	<div class=\"emailInput\">\n");
      out.write("	<input type=\"text\" id=\"email\" name=\"email\" class=\"emailArea\" placeholder = \"이메일을 입력해주세요.\" autocomplete=\"off\">\n");
      out.write("	</div>\n");
      out.write("	<button type=\"button\" id=\"emailCheckBtn\" class=\"emailBtn\" disabled=\"disabled\">이메일 인증</button>\n");
      out.write("	<p id=\"emailMsg\" class=\"emailMsg\">확인용</p>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<!-- =======================email check ======================  -->\n");
      out.write("<div class=\"div6\"> <!--인증번호 div  -->\n");
      out.write("	<div class=\"emailcText\">인증번호</div>\n");
      out.write("	<div class=\"emailcInput\">\n");
      out.write("		<input type=\"text\" maxlength=\"7\" onkeyup=\"checkAuthNum()\" id=\"AuthNumInput\" class=\"emailcArea\" disabled=\"disabled\" placeholder=\"인증번호를 입력하세요.\" onclick=\"checkAuthNum()\">\n");
      out.write("	</div>\n");
      out.write("	<p id=\"emailCheckMsg\" class=\"emailcMsg\">임시</p>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<!-- ========================birth===============  -->\n");
      out.write("\n");
      out.write("<div class=\"div7\"> <!--생년월일 div  --> \n");
      out.write("	<div class=\"birthText\">생년월일</div>\n");
      out.write("	<div class=\"birthInput\">\n");
      out.write("		<input type=\"text\" id=\"birth\" name=\"birth\" class=\"birthArea\">\n");
      out.write("	</div>\n");
      out.write("	<p class=\"birthMsg\"></p>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<!-- ================== button =========================  -->\n");
      out.write("\n");
      out.write("<div class=\"div8\">\n");
      out.write("\n");
      out.write("<button type=\"submit\" id=\"signBtn\" class=\"signBtn\" disabled=\"disabled\">회원가입</button>\n");
      out.write("<a href=\"/\"><button type=\"button\" class=\"cancleBtn\">취소하기</button></a>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("\n");
      out.write("</div> <!-- 전체를 감싸는 div끝  -->\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("</form>\n");
      out.write("\n");
      out.write("<script type=\"text/javascript\" src=\"/resources/userJoinJs.js\"></script>\n");
      out.write("\n");
      out.write("</body>\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}