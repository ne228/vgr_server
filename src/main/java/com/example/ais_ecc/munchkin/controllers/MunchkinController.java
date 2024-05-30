package com.example.ais_ecc.munchkin.controllers;


import com.example.ais_ecc.entity.User;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.payload.response.GetActionsResponse;
import com.example.ais_ecc.munchkin.payload.response.GetCardsActionsResponse;
import com.example.ais_ecc.munchkin.payload.response.GetCardsResponse;
import com.example.ais_ecc.munchkin.payload.response.GetFightOrdersResponse;
import com.example.ais_ecc.munchkin.service.MunchkinContextHandler;
import com.example.ais_ecc.payload.response.MessageResponse;
import com.example.ais_ecc.repositories.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//@CrossOrigin(origins = "*")
@CrossOrigin(origins = "http://localhost:5173") // Укажите ваш домен
@RestController
@RequestMapping("/api/munchkin")
public class MunchkinController {

    final UserRepository userRepository;
    final MunchkinContextHandler contextHandler;


    public MunchkinController(SimpMessagingTemplate messagingTemplate, UserRepository userRepository, MunchkinContextHandler contextHandler) {

        this.userRepository = userRepository;
        this.contextHandler = contextHandler;
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Проверяем, аутентифицирован ли пользователь и является ли объект Principal объектом User
        if (authentication != null) {
            // Получаем объект User
            var userDetailsImpl = (User) authentication.getPrincipal();
            User user = userRepository.findByUsername(userDetailsImpl.getUsername()).get();
//            System.out.println("Текущий пользова
//            тель: " + user.getUsername());
            return user;
        } else {
            System.out.println("Пользователь не аутентифицирован или не представлен объект User");
        }
        return null;
    }


    @GetMapping("/get_contexts")
    public ResponseEntity<?> getContexts() {
        try {
            var user = getUser();
            var contexts = contextHandler.getContextsByUser(user);

            return ResponseEntity
                    .ok()
                    .body(contexts);
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }
    }

    @GetMapping("/create_context")
    public ResponseEntity<?> createContext() {
        try {
            var context = contextHandler.createGame();

            return ResponseEntity
                    .ok()
                    .body(context);
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }

    }

    @GetMapping("/get_actions/{id}")
    public ResponseEntity<?> getActions(@PathVariable String id) {
        try {
            var context = contextHandler.getContext(id);
            var actions = context.getResolvedActions();

            GetActionsResponse getActionsResponse = new GetActionsResponse(actions);

            return ResponseEntity
                    .ok()
                    .body(getActionsResponse.toString());
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }
    }

    @GetMapping("/get_context/{id}")
    public ResponseEntity<?> getContextById(@PathVariable String id) {
        // Ваша логика обработки здесь
        try {
            var context = contextHandler.getContext(id);

            return ResponseEntity
                    .ok()
                    .body(context);
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }
    }


    @GetMapping("/connect/{id}")
    public ResponseEntity<?> connect(@PathVariable String id) {
        try {
            var user = getUser();
            var munchkinContext = contextHandler.getContext(id);
            if (munchkinContext == null)
                throw new Exception("Not found game id " + id);

            if (munchkinContext.isGameStarted)
                throw new Exception("Not connected. Game id " + id + " already started.");

            munchkinContext.addPlayer(user);
            return ResponseEntity
                    .ok()
                    .body(("User " + user.getUsername() + " success connected!"));
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }
    }

    @GetMapping("/disconnect/{id}")
    public ResponseEntity<?> disconnect(@PathVariable String id) {
        try {
            var user = getUser();
            var munchkinContext = contextHandler.getContext(id);
            if (munchkinContext == null)
                throw new Exception("Not found game id " + id);

            if (munchkinContext.isGameStarted)
                throw new Exception("Not disconnected. Game id " + id + " already started.");

            munchkinContext.removePlayer(user);

            return ResponseEntity
                    .ok()
                    .body(("User " + user.getUsername() + " success disconected!"));
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }

    }

    @GetMapping("/start/{id}")
    public ResponseEntity<?> start(@PathVariable String id) {
        try {
            var munchkinContext = contextHandler.getContext(id);
            if (munchkinContext == null)
                throw new Exception("Not found game id " + id);

            munchkinContext.startGame();

            return ResponseEntity
                    .ok()
                    .body(("Game success started!"));
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }
    }

    @GetMapping("/kick_door/{id}")
    public ResponseEntity<?> kickDoor(@PathVariable String id) {
        try {
            var munchkinContext = contextHandler.getContext(id);
            if (munchkinContext == null)
                throw new Exception("Not found game id " + id);


            var response = munchkinContext.kickDoor();
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse(response));
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }

    }

    @GetMapping("/get_cards/{id}")
    public ResponseEntity<?> getCards(@PathVariable String id) {
        try {
            var munchkinContext = contextHandler.getContext(id);
            if (munchkinContext == null)
                throw new Exception("Not found game id " + id);


            var response = new GetCardsResponse(munchkinContext.getPlayerCards());
            return ResponseEntity
                    .ok()
                    .body((response.toString()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }

    }

    @GetMapping("/get_card_action/{id}")
    public ResponseEntity<?> getCards(@PathVariable String id, @RequestParam String cardId) {
        try {
            var munchkinContext = contextHandler.getContext(id);
            if (munchkinContext == null)
                throw new Exception("Not found game id " + id);


            var response = new GetCardsActionsResponse(munchkinContext.getCardAction(cardId));
            return ResponseEntity
                    .ok()
                    .body(response.toString());
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }
    }

    @GetMapping("/agree_fight/{id}")
    public ResponseEntity<?> agreeFight(@PathVariable String id) {
        try {
            var munchkinContext = contextHandler.getContext(id);
            if (munchkinContext == null)
                throw new Exception("Not found game id " + id);


            munchkinContext.agreeFight();
            return ResponseEntity
                    .ok()
                    .body("ok");
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }
    }

    @GetMapping("/play_card/{id}")
    public ResponseEntity<?> playCard(@PathVariable String id,
                                      @RequestParam String cardId,
                                      @RequestParam(required = false) String playerId,
                                      @RequestParam(required = false) Boolean isHarm,
                                      @RequestParam(required = false) String targetCardId) {
        try {
            var munchkinContext = contextHandler.getContext(id);
            if (munchkinContext == null)
                throw new Exception("Not found game id " + id);

            PlayCardRequest request = new PlayCardRequest(cardId, playerId, isHarm, targetCardId);

            munchkinContext.playCard(request);


            var response = "null";// new GetCardsActionsResponse(munchkinContext.getCardAction(cardId));
            return ResponseEntity
                    .ok()
                    .body(response.toString());
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }
    }

    @GetMapping("/transfer_card/{id}")
    public ResponseEntity<?> transferCard(@PathVariable String id,
                                          @RequestParam String cardId,
                                          @RequestParam(required = false) String playerId,
                                          @RequestParam(required = false) Boolean isHarm,
                                          @RequestParam(required = false) String targetCardId) {
        try {
            var munchkinContext = contextHandler.getContext(id);
            if (munchkinContext == null)
                throw new Exception("Not found game id " + id);

            PlayCardRequest request = new PlayCardRequest(cardId, playerId, isHarm, targetCardId);

            munchkinContext.transferCard(request);


            var response = "null";// new GetCardsActionsResponse(munchkinContext.getCardAction(cardId));
            return ResponseEntity
                    .ok()
                    .body(response.toString());
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }
    }

    @GetMapping("/sell_card/{id}")
    public ResponseEntity<?> sellCard(@PathVariable String id,
                                      @RequestParam String cardId,
                                      @RequestParam(required = false) String playerId,
                                      @RequestParam(required = false) Boolean isHarm,
                                      @RequestParam(required = false) String targetCardId) {
        try {
            var munchkinContext = contextHandler.getContext(id);
            if (munchkinContext == null)
                throw new Exception("Not found game id " + id);

            PlayCardRequest request = new PlayCardRequest(cardId, playerId, isHarm, targetCardId);

            munchkinContext.sellCard(request);


            var response = "null";// new GetCardsActionsResponse(munchkinContext.getCardAction(cardId));
            return ResponseEntity
                    .ok()
                    .body(response.toString());
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }
    }

    @GetMapping("/level_up/{id}")
    public ResponseEntity<?> levelUp(@PathVariable String id) {
        try {
            var munchkinContext = contextHandler.getContext(id);
            if (munchkinContext == null)
                throw new Exception("Not found game id " + id);


            munchkinContext.levelUp();


            var response = "null";// new GetCardsActionsResponse(munchkinContext.getCardAction(cardId));
            return ResponseEntity
                    .ok()
                    .body(response.toString());
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }
    }

    @GetMapping("/create_fight_order/{id}")
    public ResponseEntity<?> createFightOrder(@PathVariable String id,
                                              @RequestParam(required = true) int treasureCount) {
        try {
            var munchkinContext = contextHandler.getContext(id);
            if (munchkinContext == null)
                throw new Exception("Not found game id " + id);

            var res = munchkinContext.createFightOrder(treasureCount);


            var response = "null";// new GetCardsActionsResponse(munchkinContext.getCardAction(cardId));
            return ResponseEntity
                    .ok()
                    .body(res);
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }
    }

    @GetMapping("/get_fight_orders/{id}")
    public ResponseEntity<?> getFightOrders(@PathVariable String id) {
        try {
            var munchkinContext = contextHandler.getContext(id);
            if (munchkinContext == null)
                throw new Exception("Not found game id " + id);


            var res = new GetFightOrdersResponse(munchkinContext.getFightOrders());
            var response = "null";// new GetCardsActionsResponse(munchkinContext.getCardAction(cardId));
            return ResponseEntity
                    .ok()
                    .body(res);
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }
    }

    @GetMapping("/confirm_fight_order/{id}")
    public ResponseEntity<?> confirmFightOrder(@PathVariable String id,
                                               String orderId,
                                               boolean confirm) {
        try {
            var munchkinContext = contextHandler.getContext(id);
            if (munchkinContext == null)
                throw new Exception("Not found game id " + id);


            munchkinContext.confirmFightOrder(orderId, confirm);
            var response = "null";// new GetCardsActionsResponse(munchkinContext.getCardAction(cardId));
            return ResponseEntity
                    .ok()
                    .body(response);
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<?> showContext(@PathVariable String id) {
        try {
            var munchkinContext = contextHandler.getContext(id);
            if (munchkinContext == null)
                throw new Exception("Not found game id " + id);

            return ResponseEntity
                    .ok()
                    .body((munchkinContext.toString()));
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }
    }

    @GetMapping("/fight_end/{id}")
    public ResponseEntity<?> fightEnd(@PathVariable String id) {
        try {
            var user = getUser();
            var munchkinContext = contextHandler.getContext(id);
            if (munchkinContext == null)
                throw new Exception("Not found game id " + id);

            munchkinContext.fightEnd();
            return ResponseEntity
                    .ok()
                    .body(("Player " + user.getUsername() + " fight end!"));
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }
    }

    @GetMapping("/flushing_end/{id}")
    public ResponseEntity<?> flushingEnd(@PathVariable String id) {
        try {
            var user = getUser();
            var munchkinContext = contextHandler.getContext(id);
            if (munchkinContext == null)
                throw new Exception("Not found game id " + id);

            munchkinContext.flushingEnd();
            return ResponseEntity
                    .ok()
                    .body(("User " + user.getUsername() + " success connected!"));
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }
    }

    @GetMapping("/flushing_roll/{id}")
    public ResponseEntity<?> flushingRoll(@PathVariable String id) {
        try {
            var user = getUser();
            var munchkinContext = contextHandler.getContext(id);
            if (munchkinContext == null)
                throw new Exception("Not found game id " + id);

            munchkinContext.flushingRoll();
            return ResponseEntity
                    .ok()
                    .body(("User " + user.getUsername() + " success connected!"));
        } catch (ExpiredJwtException exc) {
            return ResponseEntity
                    .status(401)
                    .body(("Error: " + exc.getMessage()));
        } catch (Exception exc) {
            return ResponseEntity
                    .status(403)
                    .body(("Error: " + exc.getMessage()));
        }
    }

    @GetMapping("/{id}/image/{cardId}/")
    public ResponseEntity<byte[]> getImage(@PathVariable String id, @PathVariable String cardId) throws Exception {
//        var user = getUser();
        var munchkinContext = contextHandler.getContext(id);
        if (munchkinContext == null)
            throw new Exception("Not found game id " + id);

        var card = munchkinContext.getPlayerCardById(cardId);
        if (card != null) {
            // Путь к изображению на диске
            Path imagePath = Paths.get("src/main/resources/static" + card.getIconPath());
            byte[] imageBytes = Files.readAllBytes(imagePath);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG); // установите правильный тип контента

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
