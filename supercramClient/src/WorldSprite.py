'''
WorldSprite superclass, represents world collision boxes, trigger zones
'''
import pygame,cereal

class WorldSprite():
    def __init__(self, left, top, width, height):
        self.collisions = True
        self.background = True
        self.trigger = False
        self.image = ''
        self.rect = pygame.Rect(left, top, width, height)
        self.params = {}
    def toTag(self):
        sprite = {}
        c = [cereal.TagInt(self.rect.left), cereal.TagInt(self.rect.top), cereal.TagInt(self.rect.width), cereal.TagInt(self.rect.height)]
        gj = cereal.TagArray(c)
        sprite["aabb"] = gj
        sprite["collisions"] = cereal.TagBool(self.collisions)
        sprite["background"] = cereal.TagBool(self.background)
        sprite["trigger"] = cereal.TagBool(self.trigger)
        tagParams = {}
        for param in self.params:
            tagParams[param] = cereal.TagString(self.params[param])
        sprite["params"] = cereal.TagMap(tagParams)
        sprite = cereal.TagMap(sprite)
        return sprite
    def __repr__(self):
        s = ""
        if(self.collisions): s += "collisions "
        if(self.background): s += "background "
        s += "rect="+str(self.rect)
        s += "params="+str(self.params)
        return s
def tagToSprite(TAGMap):
    spriteDict = TAGMap.data
    spr = WorldSprite(0,0,0,0)
    rect = spriteDict["aabb"].tags
    spr.rect = pygame.Rect(rect[0].i, rect[1].i, rect[2].i, rect[3].i)
    spr.collisions = spriteDict["collisions"].bool
    spr.background = spriteDict["background"].bool
    spr.trigger = spriteDict["trigger"].bool
    paramMap = spriteDict["params"].data
    spr.params = {}
    for key in paramMap:
        spr.params[key] = paramMap[key].s
    return spr
