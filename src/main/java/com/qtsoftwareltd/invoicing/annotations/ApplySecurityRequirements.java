package com.qtsoftwareltd.invoicing.annotations;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;

@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
public @interface ApplySecurityRequirements {
}