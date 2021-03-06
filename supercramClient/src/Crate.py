'''
Crate subclass extends Entity
'''
from Entity import Entity
import pygame, const, random

class Crate(Entity):
    def __init__(self):
        Entity.__init__(self)
        self.contents = random.choice(const.weapons)
    def update(self, world):
        Entity.update(self, world)
        for p in world.players:
            if self.rect.colliderect(p.rect):
                p.weapon = self.contents
                newWep = random.choice(const.weapons)
                while newWep == p.weapon:
                    newWep = random.choice(const.weapons)
                self.contents = newWep
                self.momentum = [0,0]
                ##########################################
                self.rect.topleft = [500,200]
        