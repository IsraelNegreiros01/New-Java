package Chamado_Emergencia.controllers;

import Chamado_Emergencia.dtos.DashboardIndicadoresDTO;
import Chamado_Emergencia.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/indicadores")
    public ResponseEntity<DashboardIndicadoresDTO> obterIndicadores() {
        DashboardIndicadoresDTO indicadores = dashboardService.obterIndicadores();
        return ResponseEntity.ok(indicadores);
    }
}
