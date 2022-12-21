package com.KOB.BotRunningSystem.Service.Impl.Utilies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bot{
    Integer userId;
    String botCode;
    String input;
}
