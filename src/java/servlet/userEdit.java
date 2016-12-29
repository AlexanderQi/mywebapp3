/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import DAO.Mydao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

/**
 *
 * @author Qi
 */
public class userEdit extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        JSONObject jo = null;
        PrintWriter out = response.getWriter();
        try {
            request.setCharacterEncoding("UTF-8");
            String username = request.getParameter("用户名");
            //String code = request.getParameter("编号");
            String id = request.getParameter("id");
            String sql = "update tbluser t set t.USERNAME='" + username + "' where t.ID='" + id+"';";
            int ur = Mydao.Update(sql);

            jo = new JSONObject();
            jo.put("success", true);
            jo.put("sql value", ur);
            jo.put("sql", sql);
            //System.out.println(id+"   "+username);

        } catch (Exception e) {
            jo.put("msg", e.toString());
            e.printStackTrace();
        } finally {
            if (jo != null) {
                //JSONArray array = new JSONArray();
                //array.add(jo);
                
                out.print(jo.toString());
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
