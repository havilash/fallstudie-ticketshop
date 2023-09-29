package ch.bbcag.backend.ticketshop.security;

import ch.bbcag.backend.ticketshop.person.PersonResponseDTO;
import ch.bbcag.backend.ticketshop.person.PersonService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AuthController.PATH)
public class AuthController {

    public static final String PATH = "/auth";
    private final PersonService personService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(PersonService personService, AuthenticationManager authenticationManager) {
        this.personService = personService;
        this.authenticationManager = authenticationManager;
    }

    private static String generateJwtToken(String userName, Collection<? extends GrantedAuthority> authorities) {
        try {
            JWSSigner signer = new MACSigner(SecurityConstants.SECRET_KEY_SPEC);
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(userName)
                    .claim("scope", authorities.stream().map(auth -> (GrantedAuthority) auth).map(GrantedAuthority::getAuthority).collect(Collectors.joining(" ")))
                    .expirationTime(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))

                    .build();

            SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            jwt.sign(signer);
            return jwt.serialize();


        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/signup")
    @Operation(summary = "Create a new person")
    @SecurityRequirements //no security here, default is BEARER
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Person was created successfully",
                    content = @Content(schema = @Schema(implementation = PersonResponseDTO.class))),
            @ApiResponse(responseCode = "409", description = "Person could not be created, email already in use",
                    content = @Content)
    })
    public ResponseEntity<?> signUp(@Valid @RequestBody AuthRequestDTO authenticationDTO) {
        if (personService.existsByEmail(authenticationDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Sign up failed, email already in use");
        }

        PersonResponseDTO newPerson = personService.create(authenticationDTO);

        AuthResponseDTO response = new AuthResponseDTO(newPerson.getId(), newPerson.getEmail());
        return ResponseEntity.status(201).body(response);
    }


    @PostMapping("/signin")
    @Operation(summary = "Receive a token for BEARER authorization")
    @SecurityRequirements //no security here, default is BEARER
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = JwtResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content)
    })
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDTO authenticationDTO) {

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            authenticationDTO.getEmail(), authenticationDTO.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();
            PersonResponseDTO person = personService.findByEmail(user.getUsername());

            String jwt = generateJwtToken(user.getUsername(), authentication.getAuthorities());

            JwtResponseDTO response = new JwtResponseDTO(jwt,
                    person.getId(),
                    person.getEmail(),
                    authentication.getAuthorities());

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }

}
