package controller;
import config.Conexion;
import entidad.Nota;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Controlador {
    
    Conexion conexion = new Conexion();
    JdbcTemplate jdbcTemplate = new JdbcTemplate(conexion.Conectar());
    ModelAndView modelAndView =  new ModelAndView();
    List datos;
    int id;
    
    @RequestMapping("index.htm")
    public ModelAndView listar(){
        String sql = "select * from notas";
        datos = this.jdbcTemplate.queryForList(sql);
        modelAndView.addObject("lista", datos);
        modelAndView.setViewName("index");
        return modelAndView;
    }
    
    @RequestMapping(value="info.htm", method = RequestMethod.GET)
    public ModelAndView VerInfo(HttpServletRequest request){
        id = Integer.parseInt(request.getParameter("id"));
        String sql = "select * from notas where id = " + id;
        datos = this.jdbcTemplate.queryForList(sql);
        System.out.println(datos);
        boolean isEmptyDatos = datos.isEmpty();
        System.out.println(isEmptyDatos);
        if (isEmptyDatos == true) {
            return new ModelAndView("redirect:/index.htm");
        }
        modelAndView.addObject("lista", datos);
        modelAndView.setViewName("info");
        return modelAndView;
    }
    
    @RequestMapping(value="editar.htm", method = RequestMethod.GET)
    public ModelAndView Editar(HttpServletRequest request){
        id = Integer.parseInt(request.getParameter("id"));
        String sql = "select * from notas where id = " + id;
        datos = this.jdbcTemplate.queryForList(sql);
        System.out.println(datos);
        boolean isEmptyDatos = datos.isEmpty();
        System.out.println(isEmptyDatos);
        if (isEmptyDatos == true) {
            return new ModelAndView("redirect:/index.htm");
        }
        modelAndView.addObject("lista", datos);
        modelAndView.setViewName("editar");
        return modelAndView;   
    }
    
    @RequestMapping(value = "editar.htm", method = RequestMethod.POST)
    public ModelAndView editar(Nota nota){
        String sql = "update notas set Notas=? where Id=?";
        this.jdbcTemplate.update(sql, nota.getNotas(), nota.getId());
        return new ModelAndView("redirect:/index.htm");
    }
    
    @RequestMapping(value = "eliminar.htm", method = RequestMethod.GET)
    public ModelAndView eliminar(HttpServletRequest request){
        id = Integer.parseInt(request.getParameter("id"));
        String sql = "delete from notas where id = " + id;
        this.jdbcTemplate.update(sql);
        return new ModelAndView("redirect:/index.htm");
    }
    
    @RequestMapping(value = "agregar.htm", method = RequestMethod.GET)
    public ModelAndView agregar(){
        modelAndView.addObject(new Nota());
        modelAndView.setViewName("agregar");
        return modelAndView;
    }
 
    @RequestMapping(value = "agregar.htm", method = RequestMethod.POST)
    public ModelAndView agregar(Nota nota){
        String sql = "insert into notas (Notas) values (?)";
        this.jdbcTemplate.update(sql, nota.getNotas());
        return new ModelAndView("redirect:/index.htm");
    }
    
}
