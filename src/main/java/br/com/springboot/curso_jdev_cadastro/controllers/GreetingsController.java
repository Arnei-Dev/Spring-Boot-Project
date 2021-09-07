package br.com.springboot.curso_jdev_cadastro.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_cadastro.models.Usuario;
import br.com.springboot.curso_jdev_cadastro.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired /*IC /CD OU CDI - Injeção de dependencia*/
	private UsuarioRepository usuarioRepository;
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/test{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso Spring Boot API: " + name + "!";
    }

    @RequestMapping(value = "/olamundo")//This is another method to create a requestMapping to show on the browser
    public String retornoOlaMundo() {
    	return "Ola Mundo !!!!";
    }
   
    @RequestMapping(value = "/insertInf/{nome}", method = RequestMethod.GET)//method to save data into db
    @ResponseStatus(HttpStatus.OK)
    public String insertData(@PathVariable String nome) {
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	usuario.setIdade(25);
    	usuarioRepository.save(usuario);//Save new information into the database
    	return "Nome: " + nome;
    }
    
    @GetMapping(value = "/listados")
    @ResponseBody /* Returns data to response body*/
    public ResponseEntity<List<Usuario>> listUser(){
    	List<Usuario> users = usuarioRepository.findAll();
    	return new ResponseEntity<List<Usuario>>(users, HttpStatus.OK);//Returns a list in Json file
    }
    
    @PostMapping(value = "/salvar")/*mapeia url*/
    @ResponseBody //Descrição da Resposta
    public ResponseEntity<Usuario> salvar (@RequestBody Usuario usuario){
    	Usuario user = usuarioRepository.save(usuario);
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    }

    //Delete from database
    @DeleteMapping(value = "/deletar")/*mapeia url*/
    @ResponseBody //Descrição da Resposta
    public ResponseEntity<String> deletar (@RequestParam Long iduser){
    	usuarioRepository.deleteById(iduser);
    	return new ResponseEntity<String>("User deletado com sucesso!!!", HttpStatus.OK);
    }
    
    //Pesquisa por id
    @GetMapping(value = "/buscarPorId")/*mapeia url*/
    @ResponseBody //Descrição da Resposta
    public ResponseEntity<Usuario> buscaruserid (@RequestParam(name = "iduser") Long iduser){
    	Usuario usuario = usuarioRepository.findById(iduser).get();
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }
    
    @PutMapping(value = "/atualizar")/*mapeia url*/
    @ResponseBody //Descrição da Resposta
    public ResponseEntity<?> atualizar (@RequestBody Usuario usuario){//Generic ? retorna tanto String como objeto "Usuario"
    	if (usuario.getId() == null) {
    		return new ResponseEntity<String>("ID não informado.", HttpStatus.OK);
    	}
    	Usuario user = usuarioRepository.saveAndFlush(usuario);
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }
    
    @GetMapping(value = "/buscarPorNome")/*mapeia url*/
    @ResponseBody //Descrição da Resposta
    public ResponseEntity<List<Usuario>> buscarPorNome (@RequestParam(name = "name") String name){
    	List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
    	return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
    }
}
