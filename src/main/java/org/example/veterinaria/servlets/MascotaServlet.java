package org.example.veterinaria.servlets;

import org.example.veterinaria.beans.Dueno;
import org.example.veterinaria.beans.Especie;
import org.example.veterinaria.beans.Mascota;
import org.example.veterinaria.beans.Veterinario;
import org.example.veterinaria.daos.MascotaDao;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "MascotaServlet", urlPatterns = "/inicio")
public class MascotaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        MascotaDao mascotaDao = new MascotaDao();

        switch (action) {
            case "lista":
                // mostrar la lista de mascotas y cargar las especies para el filtro
                request.setAttribute("listaMascotas", mascotaDao.listarMascotas());
                request.setAttribute("listaEspecies", mascotaDao.listarEspecies());
                request.getRequestDispatcher("listaMascotas.jsp").forward(request, response);
                break;

            case "nuevo":
                // cargar listas para los comboboxes del formulario de registro
                request.setAttribute("listaEspecies", mascotaDao.listarEspecies());
                request.setAttribute("listaVeterinarios", mascotaDao.listarVeterinarios());
                request.setAttribute("listaDuenos", mascotaDao.listarDuenos());
                request.getRequestDispatcher("formNuevaMascota.jsp").forward(request, response);
                break;

            case "borrar":
                // eliminar mascota y redirigir a la lista principal
                int idBorrar = Integer.parseInt(request.getParameter("id"));
                mascotaDao.borrar(idBorrar);
                response.sendRedirect(request.getContextPath() + "/inicio");
                break;

            case "filtrar":
                // filtrar por especie desde el combobox de la vista principal
                String idEspecieStr = request.getParameter("idEspecie");

                if (idEspecieStr != null && !idEspecieStr.isEmpty() && !idEspecieStr.equals("todas")) {
                    int idEspecie = Integer.parseInt(idEspecieStr);
                    request.setAttribute("listaMascotas", mascotaDao.listarMascotasPorEspecie(idEspecie));
                } else {
                    // si selecciona "Todas las especies", mostramos la lista completa
                    request.setAttribute("listaMascotas", mascotaDao.listarMascotas());
                }
                // siempre debemos enviar la lista de especies para que el combobox no desaparezca
                request.setAttribute("listaEspecies", mascotaDao.listarEspecies());
                request.getRequestDispatcher("listaMascotas.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action") == null ? "guardar" : request.getParameter("action");
        MascotaDao mascotaDao = new MascotaDao();

        if ("guardar".equals(action)) {
            // instanciar la mascota principal
            Mascota m = new Mascota();
            m.setNombre(request.getParameter("nombre"));
            m.setEdad(Integer.parseInt(request.getParameter("edad")));
            m.setPeso(Double.parseDouble(request.getParameter("peso")));

            // instanciar y setear la especie desde el combobox
            Especie e = new Especie();
            e.setIdEspecie(Integer.parseInt(request.getParameter("especie_id")));
            m.setEspecie(e);

            // instanciar y setear el veterinario desde el combobox
            Veterinario v = new Veterinario();
            v.setIdVeterinario(Integer.parseInt(request.getParameter("veterinario_id")));
            m.setVeterinario(v);

            // instanciar y setear el dueño desde el combobox
            Dueno d = new Dueno();
            d.setIdDueno(Integer.parseInt(request.getParameter("dueno_id")));
            m.setDueno(d);

            // guardar en base de datos
            mascotaDao.crear(m);

            // redirigir a la lista principal
            response.sendRedirect(request.getContextPath() + "/inicio");
        }
    }
}